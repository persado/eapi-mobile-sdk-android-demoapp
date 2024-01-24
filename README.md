# Persado Android SDK Example

This Android project serves as an example project demonstrating the integration of the Persado Mobile SDK. The app showcases a mock online shop where you can explore the implementation of the SDK in a practical setting.

The project is written in Kotlin/Compose and developed using Android Studio Giraffe | 2022.3.1. To get started, follow the instructions below.


## Installation

1. Clone the repository to your local machine:

2. Open project using Android Studio.

3. Fill in your credentials for the Persado's Maven GitHub repository in project's `gradle.properties` or in your `~/.gradle/gradle.properties` file. The following properties are required:
    - `mavenUser=`: This is your GitHub username.
    - `mavenToken=`: This is your GitHub token.

## Configuration

Before running the app, you need to configure the Persado Mobile SDK with your specific settings. Follow the steps below to set up the required values.

Open `/ui/Constants.kt`, find and fill in the following variables:
- `APP_ID`: This is the App ID of your Persado project.
- `CAMPAIGN_IDENTIFIER`: This is the label of the campaign that is configured under your Persado account.
- `TOUCHPOINT_NAME`: This is the label of the touch point that the Persado SDK will be populating.


## Usage

The Persado Mobile SDK is integrated into a mock online shop within the app. This allows you to see how the SDK is implemented and interact with it. Additionally, some hardcoded content is provided for demonstration purposes.

### Products

The app includes a file called `products.json` located in the `assets` directory. This file contains information about the available products in the mock online shop. You can modify this file to add, remove, or update products as needed.

### Users

The `users.json` file, also located in the `assets` directory, contains mock data of different Personas that can be selected as the current user browsing the store. You can modify this file to include different user profiles or update existing ones.

## Running the App

Once you have configured the Persado Mobile SDK and made any necessary changes to the product and user data, you can run the app on an Android emulator or device. Simply build and run the app using Android Studio. You can also build an apk and sideload it onto a device.


## License

This project is licensed under the [BSD 3-Clause License](https://opensource.org/license/bsd-3-clause/).

```
BSD 3-Clause License

Copyright (c) 2022, Persado
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
```