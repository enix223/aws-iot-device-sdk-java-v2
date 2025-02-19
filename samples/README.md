# Sample apps for the AWS IoT Device SDK for Java v2

* [BasicPubSub](#basicpubsub)
* [Basic Connect](#basic-connect)
* [Websocket Connect](#websocket-connect)
* [Pkcs11 Connect](#pkcs11-connect)
* [Raw Connect](#raw-connect)
* [WindowsCert Connect](#windowscert-connect)
* [X509 Connect](#x509-credentials-provider-connect)
* [CustomAuthorizer Connect](#custom-authorizer-connect)
* [JavaKeystore Connect](#java-keystore-connect)
* [CustomKeyOperationPubSub](#custom-key-operations-pubsub)
* [Shadow](#shadow)
* [Jobs](#jobs)
* [fleet provisioning](#fleet-provisioning)
* [Greengrass Discovery](#greengrass-discovery)
* [Greengrass IPC](#greengrass-ipc)
* [MQTT5 PubSub](#mqtt5-pubsub)

**Additional sample apps not described below:**

* [PubSubStress](https://github.com/aws/aws-iot-device-sdk-java-v2/tree/main/samples/PubSubStress)

Note that all samples will show their options by passing in `--help`. For example:
```sh
mvn compile exec:java -pl samples/BasicPubSub -Dexec.mainClass=pubsub.PubSub -Dexec.args='--help'
```

### Note

To enable logging in the samples, you will need to set the following system properties when running the samples:

```sh
-Daws.crt.debugnative=true
-Daws.crt.log.destination=File
-Daws.crt.log.level=Trace
-Daws.crt.log.filename=<path and filename>
```

* `aws.crt.debugnative`: Whether to debug native (C/C++) code. Can be either `true` or `false`.
* `aws.crt.log.destination`: Where the logs are outputted to. Can be `File`, `Stdout` or `Stderr`. Defaults to `Stderr`.
* `aws.crt.log.level`: The level of logging shown. Can be `Trace`, `Debug`, `Info`, `Warn`, `Error`, `Fatal`, or `None`. Defaults to `Warn`.
* `aws.crt.log.filename`: The path to save the log file. Only needed if `aws.crt.log.destination` is set to `File`.

For example, to run `BasicPubSub` with logging you could use the following:

```sh
mvn compile exec:java -pl samples/BasicPubSub -Daws.crt.debugnative=true -Daws.crt.log.level=Debug -Daws.crt.log.destionation=Stdout -Dexec.mainClass=pubsub.PubSub -Dexec.args='--endpoint <endpoint> --cert <path to cert> --key <path to key> --ca_file <path to ca file>'
```

### Running Samples with latest SDK release

If you want to run a sample using the latest release of the SDK, instead of compiled from source, you need to use the `latest-release` profile. For example:

```sh
mvn -P latest-release compile exec:java -pl samples/BasicPubSub -Dexec.mainClass=pubsub.PubSub -Dexec.args='--endpoint <endpoint> --cert <path to certificate> --key <path to private key> --ca_file <path to root CA>'
```

This will run the sample using the latest released version of the SDK rather than the version compiled from source. If you are wanting to try the samples without first compiling the SDK, then make sure to add `-P latest-release` and to have Maven download the Java V2 SDK.

## BasicPubSub

This sample uses the
[Message Broker](https://docs.aws.amazon.com/iot/latest/developerguide/iot-message-broker.html)
for AWS IoT to send and receive messages through an MQTT connection.
On startup, the device connects to the server, subscribes to a topic, and begins publishing messages to that topic. The device should receive those same messages back from the message broker, since it is subscribed to that same topic. Status updates are continually printed to the console.

source: `samples/BasicPubSub`

Your Thing's [Policy](https://docs.aws.amazon.com/iot/latest/developerguide/iot-policies.html) must provide privileges for this sample to connect. Make sure your policy allows a client ID of `test-*` to connect or use `--client_id <client ID here>` to send the client ID your policy supports.

<details>
<summary>(see sample policy)</summary>
<pre>
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "iot:Publish",
        "iot:Receive"
      ],
      "Resource": [
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/test/topic"
      ]
    },
    {
      "Effect": "Allow",
      "Action": [
        "iot:Subscribe"
      ],
      "Resource": [
        "arn:aws:iot:<b>region</b>:<b>account</b>:topicfilter/test/topic"
      ]
    },
    {
      "Effect": "Allow",
      "Action": [
        "iot:Connect"
      ],
      "Resource": [
        "arn:aws:iot:<b>region</b>:<b>account</b>:client/test-*"
      ]
    }
  ]
}
</pre>
</details>

To Run this sample, use the following command:
```sh
# Windows Platform: Windows command prompt does not support single quote, please use double quote.
mvn compile exec:java -pl samples/BasicPubSub -Dexec.mainClass=pubsub.PubSub -Dexec.args='--endpoint <endpoint> --cert <path to certificate> --key <path to private key> --ca_file <path to root CA>'
```

To run this sample using the latest SDK release, use the following command:

```sh
mvn -P latest-release compile exec:java -pl samples/BasicPubSub -Dexec.mainClass=pubsub.PubSub -Dexec.args='--endpoint <endpoint> --cert <path to certificate> --key <path to private key> --ca_file <path to root CA>'
```

## Basic Connect

This sample makes an MQTT connection using a certificate and key file. On startup, the device connects to the server using the certificate and key files, and then disconnects. This sample is for reference on connecting via certificate and key files.

Source: `samples/BasicConnect`

Your Thing's [Policy](https://docs.aws.amazon.com/iot/latest/developerguide/iot-policies.html) must provide privileges for this sample to connect. Make sure your policy allows a client ID of `test-*` to connect or use `--client_id <client ID here>` to send the client ID your policy supports.

<details>
<summary>(see sample policy)</summary>
<pre>
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "iot:Connect"
      ],
      "Resource": [
        "arn:aws:iot:<b>region</b>:<b>account</b>:client/test-*"
      ]
    }
  ]
}
</pre>
</details>

To run the basic connect sample use the following command:

```sh
mvn compile exec:java -pl samples/BasicConnect -Dexec.mainClass=basicconnect.BasicConnect -Dexec.args='--endpoint <endpoint> --cert <path to certificate> --key <path to private key> --ca_file <path to root CA>'
```

To run this sample using the latest SDK release, use the following command:

```sh
mvn -P latest-release compile exec:java -pl samples/BasicConnect -Dexec.mainClass=basicconnect.BasicConnect -Dexec.args='--endpoint <endpoint> --cert <path to certificate> --key <path to private key> --ca_file <path to root CA>'
```

## Websocket Connect

This sample makes an MQTT connection via websockets and then disconnects. On startup, the device connects to the server via websockets and then disconnects. This sample is for reference on connecting via websockets.

Source: `samples/WebsocketConnect`

Your Thing's [Policy](https://docs.aws.amazon.com/iot/latest/developerguide/iot-policies.html) must provide privileges for this sample to connect. Make sure your policy allows a client ID of `test-*` to connect or use `--client_id <client ID here>` to send the client ID your policy supports.

<details>
<summary>(see sample policy)</summary>
<pre>
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "iot:Connect"
      ],
      "Resource": [
        "arn:aws:iot:<b>region</b>:<b>account</b>:client/test-*"
      ]
    }
  ]
}
</pre>
</details>

To run the websocket connect use the following command:

```sh
mvn compile exec:java -pl samples/WebsocketConnect -Dexec.mainClass=websocketconnect.WebsocketConnect -Dexec.args='--endpoint <endpoint> --signing_region <signing region>'
```

To run this sample using the latest SDK release, use the following command:

```sh
mvn -P latest-release compile exec:java -pl samples/WebsocketConnect -Dexec.mainClass=websocketconnect.WebsocketConnect -Dexec.args='--endpoint <endpoint> --signing_region <signing region>'
```

Note that using Websockets will attempt to fetch the AWS credentials from your environment variables or local files.
See the [authorizing direct AWS](https://docs.aws.amazon.com/iot/latest/developerguide/authorizing-direct-aws.html) page for documentation on how to get the AWS credentials, which then you can set to the `AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS`, and `AWS_SESSION_TOKEN` environment variables.

## PKCS#11 Connect

This sample is similar to the [Basic Connect](#basic-connect),
but the private key for mutual TLS is stored on a PKCS#11 compatible smart card or Hardware Security Module (HSM)

WARNING: Unix only. Currently, TLS integration with PKCS#11 is only available on Unix devices.

source: `samples/Pkcs11Connect`

To run this sample using [SoftHSM2](https://www.opendnssec.org/softhsm/) as the PKCS#11 device:

1)  Create an IoT Thing with a certificate and key if you haven't already.

2)  Convert the private key into PKCS#8 format
    ```sh
    openssl pkcs8 -topk8 -in <private.pem.key> -out <private.p8.key> -nocrypt
    ```

3)  Install [SoftHSM2](https://www.opendnssec.org/softhsm/):
    ```sh
    sudo apt install softhsm
    ```

    Check that it's working:
    ```sh
    softhsm2-util --show-slots
    ```

    If this spits out an error message, create a config file:
    *   Default location: `~/.config/softhsm2/softhsm2.conf`
    *   This file must specify token dir, default value is:
        ```
        directories.tokendir = /usr/local/var/lib/softhsm/tokens/
        ```

4)  Create token and import private key.

    You can use any values for the labels, PINs, etc
    ```sh
    softhsm2-util --init-token --free --label <token-label> --pin <user-pin> --so-pin <so-pin>
    ```

    Note which slot the token ended up in

    ```sh
    softhsm2-util --import <private.p8.key> --slot <slot-with-token> --label <key-label> --id <hex-chars> --pin <user-pin>
    ```

5)  Now you can run the sample:
    ```sh
    mvn compile exec:java -pl samples/Pkcs11Connect -Dexec.mainClass=pkcs11connect.Pkcs11Connect -Dexec.args='--endpoint <endpoint> --cert <path to certificate> --ca_file <path to root CA> --pkcs11_lib <path to PKCS11 lib> --pin <user-pin> --token_label <token-label> --key_label <key-label>'
    ```

6) To run the sample using the latest SDK release:
    ```sh
    mvn -P latest-release compile exec:java -pl samples/Pkcs11Connect -Dexec.mainClass=pkcs11connect.Pkcs11Connect -Dexec.args='--endpoint <endpoint> --cert <path to certificate> --ca_file <path to root CA> --pkcs11_lib <path to PKCS11 lib> --pin <user-pin> --token_label <token-label> --key_label <key-label>'
    ```

Your Thing's [Policy](https://docs.aws.amazon.com/iot/latest/developerguide/iot-policies.html) must provide privileges for this sample to connect. Make sure your policy allows a client ID of `test-*` to connect or use `--client_id <client ID here>` to send the client ID your policy supports.

<details>
<summary>(see sample policy)</summary>
<pre>
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "iot:Connect"
      ],
      "Resource": [
        "arn:aws:iot:<b>region</b>:<b>account</b>:client/test-*"
      ]
    }
  ]
}
</pre>
</details>

## WindowsCert Connect

WARNING: Windows only

This sample shows connecting to IoT Core using mutual TLS,
but your certificate and private key are in a
[Windows certificate store](https://docs.microsoft.com/en-us/windows-hardware/drivers/install/certificate-stores),
rather than simply being files on disk.

To run this sample you need the path to your certificate in the store,
which will look something like:
"CurrentUser\MY\A11F8A9B5DF5B98BA3508FBCA575D09570E0D2C6"
(where "CurrentUser\MY" is the store and "A11F8A9B5DF5B98BA3508FBCA575D09570E0D2C6" is the certificate's thumbprint)

If your certificate and private key are in a
[TPM](https://docs.microsoft.com/en-us/windows/security/information-protection/tpm/trusted-platform-module-overview),
you would use them by passing their certificate store path.

source: `samples/WindowsCertConnect`

To run this sample with a basic certificate from AWS IoT Core:

1)  Create an IoT Thing with a certificate and key if you haven't already.

2)  Combine the certificate and private key into a single .pfx file.

    You will be prompted for a password while creating this file. Remember it for the next step.

    If you have OpenSSL installed:
    ```powershell
    openssl pkcs12 -in certificate.pem.crt -inkey private.pem.key -out certificate.pfx
    ```

    Otherwise use [CertUtil](https://docs.microsoft.com/en-us/windows-server/administration/windows-commands/certutil).
    ```powershell
    certutil -mergePFX certificate.pem.crt,private.pem.key certificate.pfx
    ```

3)  Add the .pfx file to a Windows certificate store using PowerShell's
    [Import-PfxCertificate](https://docs.microsoft.com/en-us/powershell/module/pki/import-pfxcertificate)

    In this example we're adding it to "CurrentUser\My"

    ```powershell
    $mypwd = Get-Credential -UserName 'Enter password below' -Message 'Enter password below'
    Import-PfxCertificate -FilePath certificate.pfx -CertStoreLocation Cert:\CurrentUser\My -Password $mypwd.Password
    ```

    Note the certificate thumbprint that is printed out:
    ```
    Thumbprint                                Subject
    ----------                                -------
    A11F8A9B5DF5B98BA3508FBCA575D09570E0D2C6  CN=AWS IoT Certificate
    ```

    So this certificate's path would be: "CurrentUser\MY\A11F8A9B5DF5B98BA3508FBCA575D09570E0D2C6"

4) Now you can run the sample:

    ```sh
    mvn compile exec:java -pl samples/WindowsCertConnect "-Dexec.mainClass=windowscertconnect.WindowsCertConnect" "-Dexec.args=--endpoint <endpoint> --cert <path to certificate> --ca_file <path to root CA>"
    ```

5) To run the sample using the latest SDK release:
    ```sh
    mvn -P latest-release compile exec:java -pl samples/WindowsCertConnect "-Dexec.mainClass=windowscertconnect.WindowsCertConnect" "-Dexec.args=--endpoint <endpoint> --cert <path to certificate> --ca_file <path to root CA>"
    ```

Your Thing's [Policy](https://docs.aws.amazon.com/iot/latest/developerguide/iot-policies.html) must provide privileges for this sample to connect. Make sure your policy allows a client ID of `test-*` to connect or use `--client_id <client ID here>` to send the client ID your policy supports.

<details>
<summary>(see sample policy)</summary>
<pre>
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "iot:Connect"
      ],
      "Resource": [
        "arn:aws:iot:<b>region</b>:<b>account</b>:client/test-*"
      ]
    }
  ]
}
</pre>
</details>

## x509 Credentials Provider Connect

This sample is similar to the [Basic Pub-Sub](#basic-pub-sub), but the connection uses a X.509 certificate
to source the AWS credentials when connecting.

See the [Authorizing direct calls to AWS services using AWS IoT Core credential provider](https://docs.aws.amazon.com/iot/latest/developerguide/authorizing-direct-aws.html) page for instructions on how to setup the IAM roles, the trust policy for the IAM roles, how to setup the IoT Core Role alias, and how to get the credential provider endpoint for your AWS account.

source: `samples/X509CredentialsProviderConnect`

To run the x509 Credentials Provider Connect sample use the following command:

``` sh
mvn compile exec:java -pl samples/X509CredentialsProviderConnect "-Dexec.mainClass=x509credentialsproviderconnect.X509CredentialsProviderConnect" -Dexec.args=' --endpoint <endpoint> --ca_file <path to root CA> --signing_region <signing region> --x509_ca_file <path to x509 CA> --x509_cert <path to x509 cert> --x509_endpoint <x509 credentials endpoint> --x509_key <path to x509 key> --x509_role_alias <alias> -x509_thing_name <thing name>'
```

To run the sample using the latest SDK release:

``` sh
mvn -P latest-release compile exec:java -pl samples/X509CredentialsProviderConnect "-Dexec.mainClass=x509credentialsproviderconnect.X509CredentialsProviderConnect" -Dexec.args=' --endpoint <endpoint> --ca_file <path to root CA> --signing_region <signing region> --x509_ca_file <path to x509 CA> --x509_cert <path to x509 cert> --x509_endpoint <x509 credentials endpoint> --x509_key <path to x509 key> --x509_role_alias <alias> -x509_thing_name <thing name>'
```

Your Thing's [Policy](https://docs.aws.amazon.com/iot/latest/developerguide/iot-policies.html) must provide privileges for this sample to connect. Make sure your policy allows a client ID of `test-*` to connect or use `--client_id <client ID here>` to send the client ID your policy supports.

<details>
<summary>(see sample policy)</summary>
<pre>
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "iot:Connect"
      ],
      "Resource": [
        "arn:aws:iot:<b>region</b>:<b>account</b>:client/test-*"
      ]
    },
    {
      "Effect":"Allow",
      "Action":"iot:AssumeRoleWithCertificate",
      "Resource":"arn:aws:iot:<b>region</b>:<b>account</b>:rolealias/<b>role-alias</b>"
    }
  ]
}
</pre>
</details>

## Custom Authorizer Connect

This sample makes an MQTT connection and connects through a [Custom Authorizer](https://docs.aws.amazon.com/iot/latest/developerguide/custom-authentication.html). On startup, the device connects to the server and then disconnects. This sample is for reference on connecting using a custom authorizer.

Source: `samples/CustomAuthorizerConnect`

Your Thing's [Policy](https://docs.aws.amazon.com/iot/latest/developerguide/iot-policies.html) must provide privileges for this sample to connect. Make sure your policy allows a client ID of `test-*` to connect or use `--client_id <client ID here>` to send the client ID your policy supports.

<details>
<summary>(see sample policy)</summary>
<pre>
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "iot:Connect"
      ],
      "Resource": [
        "arn:aws:iot:<b>region</b>:<b>account</b>:client/test-*"
      ]
    }
  ]
}
</pre>
</details>

To run the custom authorizer connect use the following command:

```sh
mvn compile exec:java -pl samples/CustomAuthorizerConnect -Dexec.mainClass=customauthorizerconnect.CustomAuthorizerConnect -Dexec.args='--endpoint <endpoint> --custom_auth_authorizer_name <custom authorizer name>'
```

To run this sample using the latest SDK release, use the following command:

```sh
mvn -P latest-release compile exec:java -pl samples/CustomAuthorizerConnect -Dexec.mainClass=customauthorizerconnect.CustomAuthorizerConnect -Dexec.args='--endpoint <endpoint> --custom_auth_authorizer_name <custom authorizer name>'
```

You will need to setup your Custom Authorizer so that the lambda function returns a policy document. See [this page on the documentation](https://docs.aws.amazon.com/iot/latest/developerguide/config-custom-auth.html) for more details and example return result.

## Java Keystore Connect

This sample makes an MQTT connection using a certificate and key file stored in a Java keystore file.

Source: `samples/JavaKeystoreConnect`

To use the certificate and key files provided by AWS IoT Core, you will need to convert them into PKCS12 format and then import them into your Java keystore. You can convert the certificate and key file to PKCS12 using the following command:

```sh
openssl pkcs12 -export -in <my-certificate.pem.crt> -inkey <my-private-key.pem.key> -out my-pkcs12-key.p12 -name <alias here> -password pass:<password here>
```

Once you have a PKCS12 certificate and key, you can import it into a Java keystore using the following:

```sh
keytool -importkeystore -srckeystore my-pkcs12-key.p12 -destkeystore <destination keystore> -srcstoretype pkcs12 -alias <alias here> -srcstorepass <PKCS12 password> -deststorepass <keystore password>
```

Your Thing's [Policy](https://docs.aws.amazon.com/iot/latest/developerguide/iot-policies.html) must provide privileges for this sample to connect. Make sure your policy allows a client ID of `test-*` to connect or use `--client_id <client ID here>` to send the client ID your policy supports.

<details>
<summary>(see sample policy)</summary>
<pre>
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "iot:Connect"
      ],
      "Resource": [
        "arn:aws:iot:<b>region</b>:<b>account</b>:client/test-*"
      ]
    }
  ]
}
</pre>
</details>

To run the Java keystore connect sample use the following command:

```sh
mvn compile exec:java -pl samples/JavaKeystoreConnect -Dexec.mainClass=javakeystoreconnect.JavaKeystoreConnect -Dexec.args='--endpoint <endpoint> --keystore <path to Java keystore file> --keystore_password <password for Java keystore> --certificate_alias <alias of PKCS12 certificate> --certificate_password <password for PKCS12 certificate>'
```

To run this sample using the latest SDK release, use the following command:

```sh
mvn -P latest-release compile exec:java -pl samples/JavaKeystoreConnect -Dexec.mainClass=javakeystoreconnect.JavaKeystoreConnect -Dexec.args='--endpoint <endpoint> --keystore <path to Java keystore file> --keystore_password <password for Java keystore> --certificate_alias <alias of PKCS12 certificate> --certificate_password <password for PKCS12 certificate>'
```

## Custom Key Operations PubSub

WARNING: Linux only

This sample shows how to perform custom private key operations during the Mutual
TLS handshake. This is necessary if you require an external library to handle
private key operations such as signing and decrypting.

Note that, for this sample, the `--key` passed via args must be a PKCS#8 file,
instead of the typical PKCS#1 file that AWS IoT Core vends by default. To convert
your key file from PKCS#1 (starts with "-----BEGIN RSA PRIVATE KEY-----") into
a PKCS#8 file (starts with "-----BEGIN PRIVATE KEY-----"), run the following cmd:

```sh
> openssl pkcs8 -topk8 -in <my-private.pem.key> -out <my-private-p8.pem.key> -nocrypt
```

To Run:
``` sh
> mvn exec:java -pl samples/CustomKeyOpsPubSub -Dexec.mainClass=customkeyopspubsub.CustomKeyOpsPubSub -Dexec.args='--endpoint <endpoint> --ca_file <path to root CA> --cert <path to certificate> --key <path to pkcs8 key>'
```

To run this sample using the latest SDK release, use the following command:

``` sh
> mvn -P latest-release exec:java -pl samples/CustomKeyOpsPubSub -Dexec.mainClass=customkeyopspubsub.CustomKeyOpsPubSub -Dexec.args='--endpoint <endpoint> --ca_file <path to root CA> --cert <path to certificate> --key <path to pkcs8 key>'
```

## Shadow

This sample uses the AWS IoT
[Device Shadow](https://docs.aws.amazon.com/iot/latest/developerguide/iot-device-shadows.html)
Service to keep a property in
sync between device and server. Imagine a light whose color may be changed
through an app, or set by a local user.

Once connected, type a value in the terminal and press Enter to update
the property's "reported" value. The sample also responds when the "desired"
value changes on the server. To observe this, edit the Shadow document in
the AWS Console and set a new "desired" value.

On startup, the sample requests the shadow document to learn the property's
initial state. The sample also subscribes to "delta" events from the server,
which are sent when a property's "desired" value differs from its "reported"
value. When the sample learns of a new desired value, that value is changed
on the device and an update is sent to the server with the new "reported"
value.

Source: `samples/Shadow`

To Run:

``` sh
mvn compile exec:java -pl samples/Shadow -Dexec.mainClass=shadow.ShadowSample -Dexec.args='--endpoint <endpoint> --ca_file <path to root CA> --cert <path to certificate> --key <path to private key> --thing_name <thing name>'
```

To run this sample using the latest SDK release, use the following command:

``` sh
mvn -P latest-release compile exec:java -pl samples/Shadow -Dexec.mainClass=shadow.ShadowSample -Dexec.args='--endpoint <endpoint> --ca_file <path to root CA> --cert <path to certificate> --key <path to private key> --thing_name <thing name>'
```

Your Thing's [Policy](https://docs.aws.amazon.com/iot/latest/developerguide/iot-policies.html) must provide privileges for this sample to connect, subscribe, publish, and receive. Make sure your policy allows a client ID of `test-*` to connect or use `--client_id <client ID here>` to send the client ID your policy supports.

<details>
<summary>Sample Policy</summary>
<pre>
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "iot:Publish"
      ],
      "Resource": [
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/things/<b>thingname</b>/shadow/get",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/things/<b>thingname</b>/shadow/update"
      ]
    },
    {
      "Effect": "Allow",
      "Action": [
        "iot:Receive"
      ],
      "Resource": [
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/things/<b>thingname</b>/shadow/get/accepted",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/things/<b>thingname</b>/shadow/get/rejected",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/things/<b>thingname</b>/shadow/update/accepted",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/things/<b>thingname</b>/shadow/update/rejected",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/things/<b>thingname</b>/shadow/update/delta"
      ]
    },
    {
      "Effect": "Allow",
      "Action": [
        "iot:Subscribe"
      ],
      "Resource": [
        "arn:aws:iot:<b>region</b>:<b>account</b>:topicfilter/$aws/things/<b>thingname</b>/shadow/get/accepted",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topicfilter/$aws/things/<b>thingname</b>/shadow/get/rejected",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topicfilter/$aws/things/<b>thingname</b>/shadow/update/accepted",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topicfilter/$aws/things/<b>thingname</b>/shadow/update/rejected",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topicfilter/$aws/things/<b>thingname</b>/shadow/update/delta"
      ]
    },
    {
      "Effect": "Allow",
      "Action": "iot:Connect",
      "Resource": "arn:aws:iot:<b>region</b>:<b>account</b>:client/test-*"
    }
  ]
}
</pre>
</details>

## Jobs

This sample uses the AWS IoT
[Jobs](https://docs.aws.amazon.com/iot/latest/developerguide/iot-jobs.html)
Service to describe jobs to execute.

This sample requires you to create jobs for your device to execute. See
[instructions here](https://docs.aws.amazon.com/iot/latest/developerguide/create-manage-jobs.html).

On startup, the sample describes a job that is pending execution.

Source: `samples/Jobs`

To Run:

``` sh
mvn compile exec:java -pl samples/Jobs -Dexec.mainClass=jobs.JobsSample -Dexec.args='--endpoint <endpoint> --ca_file <path to root CA> --cert <path to certificate> --key <path to private key> --thing_name <thing name>'
```

To run this sample using the latest SDK release, use the following command:

``` sh
mvn -P latest-release compile exec:java -pl samples/Jobs -Dexec.mainClass=jobs.JobsSample -Dexec.args='--endpoint <endpoint> --ca_file <path to root CA> --cert <path to certificate> --key <path to private key> --thing_name <thing name>'
```

Your Thing's [Policy](https://docs.aws.amazon.com/iot/latest/developerguide/iot-policies.html) must provide privileges for this sample to connect, subscribe, publish, and receive. Make sure your policy allows a client ID of `test-*` to connect or use `--client_id <client ID here>` to send the client ID your policy supports.

<details>
<summary>Sample Policy</summary>
<pre>
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": "iot:Publish",
      "Resource": [
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/things/<b>thingname</b>/jobs/start-next",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/things/<b>thingname</b>/jobs/*/update",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/things/<b>thingname</b>/jobs/*/get",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/things/<b>thingname</b>/jobs/get"
      ]
    },
    {
      "Effect": "Allow",
      "Action": "iot:Receive",
      "Resource": [
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/things/<b>thingname</b>/jobs/notify-next",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/things/<b>thingname</b>/jobs/start-next/*",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/things/<b>thingname</b>/jobs/*/update/*",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/things/<b>thingname</b>/jobs/get/*",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/things/<b>thingname</b>/jobs/*/get/*"
      ]
    },
    {
      "Effect": "Allow",
      "Action": "iot:Subscribe",
      "Resource": [
        "arn:aws:iot:<b>region</b>:<b>account</b>:topicfilter/$aws/things/<b>thingname</b>/jobs/notify-next",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topicfilter/$aws/things/<b>thingname</b>/jobs/start-next/*",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topicfilter/$aws/things/<b>thingname</b>/jobs/*/update/*",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topicfilter/$aws/things/<b>thingname</b>/jobs/get/*",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topicfilter/$aws/things/<b>thingname</b>/jobs/*/get/*"
      ]
    },
    {
      "Effect": "Allow",
      "Action": "iot:Connect",
      "Resource": "arn:aws:iot:<b>region</b>:<b>account</b>:client/test-*"
    }
  ]
}
</pre>
</details>

## Fleet provisioning

This sample uses the AWS IoT
[Fleet provisioning](https://docs.aws.amazon.com/iot/latest/developerguide/provision-wo-cert.html)
to provision devices using either a CSR or Keys-And-Certificate and subsequently calls RegisterThing.

On startup, the script subscribes to topics based on the request type of either CSR or Keys topics,
publishes the request to corresponding topic and calls RegisterThing.

Source: `samples/Identity`

cd ~/samples/Identity

Run the sample using CreateKeysAndCertificate:

``` sh
mvn compile exec:java -pl samples/Identity -Dexec.mainClass="identity.FleetProvisioningSample" -Dexec.args="--endpoint <endpoint> --ca_file <path to root CA>
--cert <path to certificate> --key <path to private key> --template_name <template name> --template_parameters <template params>"
```

Run the sample using CreateCertificateFromCsr:

``` sh
mvn compile exec:java -pl samples/Identity -Dexec.mainClass="identity.FleetProvisioningSample" -Dexec.args="--endpoint <endpoint> --ca_file <path to root CA>
--cert <path to certificate> --key <path to private key> --template_name <template name> --template_parameters <template params> --csr <path to csr file>"
```

To run this sample using the latest SDK release, use the following command:

``` sh
mvn -P latest-release compile exec:java -pl samples/Identity -Dexec.mainClass="identity.FleetProvisioningSample" -Dexec.args="--endpoint <endpoint> --ca_file <path to root CA>
--cert <path to certificate> --key <path to private key> --template_name <template name> --template_parameters <template params>"
```

Your Thing's [Policy](https://docs.aws.amazon.com/iot/latest/developerguide/iot-policies.html) must provide privileges for this sample to connect, subscribe, publish, and receive. Make sure your policy allows a client ID of `test-*` to connect or use `--client_id <client ID here>` to send the client ID your policy supports.

<details>
<summary>(see sample policy)</summary>
<pre>
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": "iot:Publish",
      "Resource": [
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/certificates/create/json",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/certificates/create-from-csr/json",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/provisioning-templates/<b>templatename</b>/provision/json"
      ]
    },
    {
      "Effect": "Allow",
      "Action": [
        "iot:Receive"
      ],
      "Resource": [
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/certificates/create/json/accepted",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/certificates/create/json/rejected",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/certificates/create-from-csr/json/accepted",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/certificates/create-from-csr/json/rejected",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/provisioning-templates/<b>templatename</b>/provision/json/accepted",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/$aws/provisioning-templates/<b>templatename</b>/provision/json/rejected"
      ]
    },
    {
      "Effect": "Allow",
      "Action": [
        "iot:Subscribe"
      ],
      "Resource": [
        "arn:aws:iot:<b>region</b>:<b>account</b>:topicfilter/$aws/certificates/create/json/accepted",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topicfilter/$aws/certificates/create/json/rejected",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topicfilter/$aws/certificates/create-from-csr/json/accepted",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topicfilter/$aws/certificates/create-from-csr/json/rejected",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topicfilter/$aws/provisioning-templates/<b>templatename</b>/provision/json/accepted",
        "arn:aws:iot:<b>region</b>:<b>account</b>:topicfilter/$aws/provisioning-templates/<b>templatename</b>/provision/json/rejected"
      ]
    },
    {
      "Effect": "Allow",
      "Action": "iot:Connect",
      "Resource": "arn:aws:iot:<b>region</b>:<b>account</b>:client/test-*"
    }
  ]
}
</pre>
</details>

### Fleet Provisioning Detailed Instructions

#### Aws Resource Setup

Fleet provisioning requires some additional AWS resources be set up first. This section documents the steps you need to take to
get the sample up and running. These steps assume you have the AWS CLI installed and the default user/credentials has
sufficient permission to perform all of the listed operations. You will also need python3 to be able to run parse_cert_set_result.py. These steps are based on provisioning setup steps
that can be found at [Embedded C SDK Setup](https://docs.aws.amazon.com/freertos/latest/lib-ref/c-sdk/provisioning/provisioning_tests.html#provisioning_system_tests_setup).

First, create the IAM role that will be needed by the fleet provisioning template. Replace `RoleName` with a name of the role you want to create.
``` sh
aws iam create-role \
    --role-name [RoleName] \
    --assume-role-policy-document '{"Version":"2012-10-17","Statement":[{"Action":"sts:AssumeRole","Effect":"Allow","Principal":{"Service":"iot.amazonaws.com"}}]}'
```
Next, attach a policy to the role created in the first step. Replace `RoleName` with the name of the role you created previously.
``` sh
aws iam attach-role-policy \
        --role-name [RoleName] \
        --policy-arn arn:aws:iam::aws:policy/service-role/AWSIoTThingsRegistration
```
Finally, create the template resource which will be used for provisioning by the demo application. This needs to be done only
once.  To create a template, the following AWS CLI command may be used. Replace `TemplateName` with the name of the fleet
provisioning template you want to create. Replace `RoleName` with the name of the role you created previously. Replace
`TemplateJSON` with the template body as a JSON string (containing escape characters). Replace `account` with your AWS
account number.
``` sh
aws iot create-provisioning-template \
        --template-name [TemplateName] \
        --provisioning-role-arn arn:aws:iam::[account]:role/[RoleName] \
        --template-body "[TemplateJSON]" \
        --enabled
```
The rest of the instructions assume you have used the following for the template body:

<details>
<summary>(see template body)</summary>
``` sh
{
  "Parameters": {
    "DeviceLocation": {
      "Type": "String"
    },
    "AWS::IoT::Certificate::Id": {
      "Type": "String"
    },
    "SerialNumber": {
      "Type": "String"
    }
  },
  "Mappings": {
    "LocationTable": {
      "Seattle": {
        "LocationUrl": "https://example.aws"
      }
    }
  },
  "Resources": {
    "thing": {
      "Type": "AWS::IoT::Thing",
      "Properties": {
        "ThingName": {
          "Fn::Join": [
            "",
            [
              "ThingPrefix_",
              {
                "Ref": "SerialNumber"
              }
            ]
          ]
        },
        "AttributePayload": {
          "version": "v1",
          "serialNumber": "serialNumber"
        }
      },
      "OverrideSettings": {
        "AttributePayload": "MERGE",
        "ThingTypeName": "REPLACE",
        "ThingGroups": "DO_NOTHING"
      }
    },
    "certificate": {
      "Type": "AWS::IoT::Certificate",
      "Properties": {
        "CertificateId": {
          "Ref": "AWS::IoT::Certificate::Id"
        },
        "Status": "Active"
      },
      "OverrideSettings": {
        "Status": "REPLACE"
      }
    },
    "policy": {
      "Type": "AWS::IoT::Policy",
      "Properties": {
        "PolicyDocument": {
          "Version": "2012-10-17",
          "Statement": [
            {
              "Effect": "Allow",
              "Action": [
                "iot:Connect",
                "iot:Subscribe",
                "iot:Publish",
                "iot:Receive"
              ],
              "Resource": "*"
            }
          ]
        }
      }
    }
  },
  "DeviceConfiguration": {
    "FallbackUrl": "https://www.example.com/test-site",
    "LocationUrl": {
      "Fn::FindInMap": [
        "LocationTable",
        {
          "Ref": "DeviceLocation"
        },
        "LocationUrl"
      ]
    }
  }
}
```
</details>

If you use a different body, you may need to pass in different template parameters.

#### Running the sample and provisioning using a certificate-key set from a provisioning claim

To run the provisioning sample, you'll need a certificate and key set with sufficient permissions. Provisioning certificates are normally
created ahead of time and placed on your device, but for this sample, we will just create them on the fly. You can also
use any certificate set you've already created if it has sufficient IoT permissions and in doing so, you can skip the step
that calls `create-provisioning-claim`.

We've included a script in the utils folder that creates certificate and key files from the response of calling
`create-provisioning-claim`. These dynamically sourced certificates are only valid for five minutes. When running the command,
you'll need to substitute the name of the template you previously created, and on Windows, replace the paths with something appropriate.

(Optional) Create a temporary provisioning claim certificate set. This command is executed in the debug folder(`aws-iot-device-sdk-java-v2-build\samples\identity\fleet_provisioning\Debug`):
``` sh
aws iot create-provisioning-claim \
        --template-name [TemplateName] \
        | python3 ../../../../../aws-iot-device-sdk-java-v2/utils/parse_cert_set_result.py \
        --path /tmp \
        --filename provision
```

The provisioning claim's cert and key set have been written to `/tmp/provision*`. Now you can use these temporary keys
to perform the actual provisioning. If you are not using the temporary provisioning certificate, replace the paths for `--cert`
and `--key` appropriately:

``` sh
mvn compile exec:java -pl samples/Identity -Dexec.mainClass="identity.FleetProvisioningSample" -Dexec.args="--endpoint <endpoint> --ca_file <path to root CA>
--cert <path to certificate> --key <path to private key> --template_name <template name> --template_parameters {\"SerialNumber\":\"1\",\"DeviceLocation\":\"Seattle\"}"
```

Notice that we provided substitution values for the two parameters in the template body, `DeviceLocation` and `SerialNumber`.

If you want to run the sample using the latest SDK release then you need to run the snippet below instead. If you are not using the temporary provisioning certificate, replace the paths for `--cert` and `--key` appropriately:

``` sh
mvn -P latest-release compile exec:java -pl samples/Identity -Dexec.mainClass="identity.FleetProvisioningSample" -Dexec.args="--endpoint <endpoint> --ca_file <path to root CA>
--cert <path to certificate> --key <path to private key> --template_name <template name> --template_parameters {\"SerialNumber\":\"1\",\"DeviceLocation\":\"Seattle\"}"
```

#### Run the sample using the certificate signing request workflow

To run the sample with this workflow, you'll need to create a certificate signing request.

First create a certificate-key pair:
``` sh
openssl genrsa -out /tmp/deviceCert.key 2048
```

Next create a certificate signing request from it:
``` sh
openssl req -new -key /tmp/deviceCert.key -out /tmp/deviceCert.csr
```

(Optional) As with the previous workflow, we'll create a temporary certificate set from a provisioning claim. This step can
be skipped if you're using a certificate set capable of provisioning the device. This command is executed in the debug folder(`aws-iot-device-sdk-java-v2-build\samples\identity\fleet_provisioning\Debug`):

``` sh
aws iot create-provisioning-claim \
        --template-name [TemplateName] \
        | python3 ../../../../../aws-iot-device-sdk-java-v2/utils/parse_cert_set_result.py \
        --path /tmp \
        --filename provision
```

Finally, supply the certificate signing request while invoking the provisioning sample. As with the previous workflow, if
using a permanent certificate set, replace the paths specified in the `--cert` and `--key` arguments:
``` sh
mvn compile exec:java -pl samples/Identity -Dexec.mainClass="identity.FleetProvisioningSample" -Dexec.args="--endpoint <endpoint> --ca_file <path to root CA>
--cert <path to certificate> --key <path to private key> --template_name <template name> --template_parameters {\"SerialNumber\":\"1\",\"DeviceLocation\":\"Seattle\"}  --csr <path to csr file>"
```

If you want to run the sample using the latest SDK release then you need to run the snippet below instead. If using a permanent certificate set, make sure to replace the paths specified in the `--cert` and `--key` arguments:
``` sh
mvn -P latest-release compile exec:java -pl samples/Identity -Dexec.mainClass="identity.FleetProvisioningSample" -Dexec.args="--endpoint <endpoint> --ca_file <path to root CA>
--cert <path to certificate> --key <path to private key> --template_name <template name> --template_parameters {\"SerialNumber\":\"1\",\"DeviceLocation\":\"Seattle\"}  --csr <path to csr file>"
```

## Greengrass Discovery

This sample is intended for use with the following tutorials in the AWS IoT Greengrass documentation:

* [Connect and test client devices](https://docs.aws.amazon.com/greengrass/v2/developerguide/client-devices-tutorial.html) (Greengrass V2)
* [Test client device communications](https://docs.aws.amazon.com/greengrass/v2/developerguide/test-client-device-communications.html) (Greengrass V2)
* [Getting Started with AWS IoT Greengrass](https://docs.aws.amazon.com/greengrass/latest/developerguide/gg-gs.html) (Greengrass V1)

## Greengrass IPC

This sample uses [AWS IoT Greengrass V2](https://docs.aws.amazon.com/greengrass/index.html) to publish messages from the Greengrass device to the AWS IoT MQTT broker.

This sample can be deployed as a Greengrass V2 component and it will publish 10 MQTT messages over the course of 10 seconds. The IPC integration with Greengrass V2 allows this code to run without additional IoT certificates or secrets, because it directly communicates with the Greengrass core running on the device. As such, to run this sample you need Greengrass Core running.

source: `samples/GreengrassIPC`

Some Greengrass references:

* See this page for more information on Greengrass v2 components: https://docs.aws.amazon.com/greengrass/v2/developerguide/create-components.html
* See this page for more information on IPC in Greengrass v2: https://docs.aws.amazon.com/greengrass/v2/developerguide/interprocess-communication.html
* See this page for more information on how to make a local deployment: https://docs.aws.amazon.com/greengrass/v2/developerguide/test-components.html

To run the sample, create a new AWS IoT Greengrass component and deploy it to your device with the following recipe snippet to allow your device to publish to AWS IoT Core:

<details>

```yaml
  ---
  RecipeFormatVersion: "2020-01-25"
  ComponentName: "GreengrassIPC"
  ComponentVersion: "1.0.0"
  ComponentDescription: "IPC Greengrass sample."
  ComponentPublisher: "<ComponentPublisher>"
  ComponentConfiguration:
  DefaultConfiguration:
      accessControl:
      aws.greengrass.ipc.mqttproxy:
          software.amazon.awssdk.iotdevicesdk.GreengrassIPC:1:
          policyDescription: "Allows access to publish to all AWS IoT Core topics. For demonstration only - use best practices in a real application"
          operations:
              - aws.greengrass#PublishToIoTCore
          resources:
              - "*"
  Manifests:
  - Platform:
      os: all
      Artifacts:
      - URI: "<S3 Bucket URL>/software.amazon.awssdk.iotdevicesdk/1.0.0/GreengrassIPC-1.0-SNAPSHOT.jar"
      Lifecycle:
          RequiresPrivilege: true
          Run: "java -cp {artifacts:path}/GreengrassIPC-1.0-SNAPSHOT.jar greengrass.GreengrassIPC "
```

Replace the following with your information:
 * `<ComponentPublisher>` with the name you wish to publish your component under.
 * `<S3 Bucket URL>` with the S3 bucket URL for your account to store your Greengrass components under

</details>
<br />

As an example, you could use the following `gdk-config.json` below in your component with this sample and the recipe yaml shown above:
<details>

```json
  {
  "component": {
      "software.amazon.awssdk.iotdevicesdk.GreengrassIPC": {
      "author": "<ComponentPublisher>",
      "version": "1.0.0",
      "build": {
          "build_system": "maven"
      },
      "publish": {
          "bucket": "<S3 Bucket URL>",
          "region": "<Region>"
      }
      }
  },
  "gdk_version": "1.0.0"
  }
```

Replace the following with your information:
 * `<ComponentPublisher>` with the name you wish to publish your component under.
 * `<S3 Bucket URL>` with the S3 bucket URL for your account to store your Greengrass components under.
 * `<Region>` the region of your S3 bucket and Greengrass device.

</details>
<br />

Note that you will need to have the Java V2 SDK installed on the Greengrass device in order to compile the sample on the Greengrass device.

## MQTT5 PubSub

This sample uses the
[Message Broker](https://docs.aws.amazon.com/iot/latest/developerguide/iot-message-broker.html)
for AWS IoT to send and receive messages through an MQTT connection using MQTT5.

MQTT5 introduces additional features and enhancements that improve the development experience with MQTT. You can read more about MQTT5 in the Java V2 SDK by checking out the [MQTT5 user guide](../documents/MQTT5_Userguide.md).

Note: MQTT5 support is currently in **developer preview**. We encourage feedback at all times, but feedback during the preview window is especially valuable in shaping the final product. During the preview period we may make backwards-incompatible changes to the public API, but in general, this is something we will try our best to avoid.

source: `samples/Mqtt5/PubSub`

Your Thing's [Policy](https://docs.aws.amazon.com/iot/latest/developerguide/iot-policies.html) must provide privileges for this sample to connect. Make sure your policy allows a client ID of `test-*` to connect or use `--client_id <client ID here>` to send the client ID your policy supports.

<details>
<summary>(see sample policy)</summary>
<pre>
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "iot:Publish",
        "iot:Receive"
      ],
      "Resource": [
        "arn:aws:iot:<b>region</b>:<b>account</b>:topic/test/topic"
      ]
    },
    {
      "Effect": "Allow",
      "Action": [
        "iot:Subscribe"
      ],
      "Resource": [
        "arn:aws:iot:<b>region</b>:<b>account</b>:topicfilter/test/topic"
      ]
    },
    {
      "Effect": "Allow",
      "Action": [
        "iot:Connect"
      ],
      "Resource": [
        "arn:aws:iot:<b>region</b>:<b>account</b>:client/test-*"
      ]
    }
  ]
}
</pre>
</details>

To Run this sample using a direct MQTT connection with a key and certificate, use the following command:
```sh
mvn compile exec:java -pl samples/Mqtt5/PubSub -Dexec.mainClass=mqtt5.pubsub.PubSub -Dexec.args='--endpoint <endpoint> --cert <path to certificate> --key <path to private key> --ca_file <path to root CA>'
```

To Run this sample using Websockets, use the following command:
```sh
mvn compile exec:java -pl samples/Mqtt5/PubSub -Dexec.mainClass=mqtt5.pubsub.PubSub -Dexec.args='--endpoint <endpoint> --signing_region <region>'
```

Note that to run this sample using Websockets, you will need to set your AWS credentials in your environment variables or local files. See the [authorizing direct AWS](https://docs.aws.amazon.com/iot/latest/developerguide/authorizing-direct-aws.html) page for documentation on how to get the AWS credentials, which then you can set to the `AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS`, and `AWS_SESSION_TOKEN` environment variables.
