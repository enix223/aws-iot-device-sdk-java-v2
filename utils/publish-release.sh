#!/usr/bin/env bash

set -euxo pipefail

# Redirect output to stderr.
exec 1>&2

RELEASE_TYPE="$1"
RELEASE_TITLE="$2"

# Make sure there are ONLY two arguments
if [ "$#" != "2" ]; then
    echo "ERROR: Arguments passed is NOT equal to two!"
    exit 1
fi

pushd $(dirname $0) > /dev/null

# Get the current version
git checkout main

git_tags=$(git tag)
current_version=$(python3 ./update_semantic_version.py  --version "${git_tags}" --type MINOR --parse_latest_version true)
current_version_without_v=$(echo ${current_version} | cut -f2 -dv)

echo "Current release version is ${current_version_without_v}"

# Validate that RELEASE_TYPE is what we expect and bump the version
new_version=$(python3 ./update_semantic_version.py --version "${current_version_without_v}" --type "${RELEASE_TYPE}")
if [ "$new_version" == "0.0.0" ]; then
    echo "ERROR: Unknown release type! Exitting..."
    exit -1
fi
echo "New version is ${new_version}"

# Validate that the title is set
if [ "$RELEASE_TITLE" == "" ]; then
    echo "ERROR: No title set! Cannot make release. Exitting..."
    exit -1
fi

# Setup Github credentials
git config --local user.email "aws-sdk-common-runtime@amazon.com"
git config --local user.name "GitHub Actions"

# Update version-sensitive files
# --==--
new_version_branch=AutoTag-v${new_version}
git checkout -b ${new_version_branch}

# Go from utils to the main folder
cd ..
# Update the SDK version text and the SDK version in samples
python3 ./update-crt.py
python3 ./update-crt.py ${new_version} --update_sdk_text
python3 ./update-crt.py ${new_version} --update_samples
# Update the version in the README to show the latest
sed -i -r "s/.*Latest released version:.*/Latest released version: v${new_version}/" README.md
# Not sure how to do this better, so just add each file individually
git add sdk/pom.xml
git add README.md
git add android/iotdevicesdk/build.gradle
# Add all the sample pom files
find . -name "pom.xml" -maxdepth 3 -mindepth 3 -exec git add {} +
# go back to the utils folder
cd utils

# Make the commit
git commit -m "[v$new_version] $RELEASE_TITLE"

# push the commit and create a PR
git push -u "https://${GITHUB_ACTOR}:${GITHUB_TOKEN}@github.com/aws/aws-iot-device-sdk-java-v2.git" ${new_version_branch}
gh pr create --title "AutoTag PR for v${new_version}" --body "AutoTag PR for v${new_version}" --head ${new_version_branch}

# Merge the PR
gh pr merge --admin --squash
# --==--

# Update local state with the merged pr (if one was made) and just generally make sure we're up to date
git fetch
git checkout main
git pull "https://${GITHUB_ACTOR}:${GITHUB_TOKEN}@github.com/aws/aws-iot-device-sdk-java-v2.git" main

# Create new tag on latest commit (lightweight tag - we do NOT want an annotated tag)
git tag -f v${new_version}
# Push new tag to github
git push "https://${GITHUB_ACTOR}:${GITHUB_TOKEN}@github.com/aws/aws-iot-device-sdk-java-v2.git" --tags

# Determine if this is a pre-release or not based on the major version
IS_PRE_RELEASE="false"
VERSION_STRING_DELIMITER=.
VERSION_STRING_ARRAY=($(echo "$new_version" | tr $VERSION_STRING_DELIMITER '\n'))
if [ "${VERSION_STRING_ARRAY[0]}" == "0" ]; then
    IS_PRE_RELEASE="true"
else
    IS_PRE_RELEASE="false"
fi

# Create the release with auto-generated notes as the description
# - NOTE: This will only add notes if there is at least one PR. If there is no PRs,
# - then this will be blank and need manual input/changing after running.
if [ $IS_PRE_RELEASE == "true" ]; then
    gh release create "v${new_version}" -p --generate-notes --notes-start-tag "$current_version" --target main -t "${RELEASE_TITLE}"
else
    gh release create "v${new_version}" --generate-notes --notes-start-tag "$current_version" --target main -t "${RELEASE_TITLE}"
fi

popd > /dev/null
