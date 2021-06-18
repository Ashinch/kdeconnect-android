# KDE Connect - Android app

KDE Connect is a multi-platform app that allows your devices to communicate (eg: your phone and your computer).

This repo is personal modified version, additional features:

- Added ability to notifications when a connection is lost or received. (branch: [notifications-connect-status](https://github.com/Ashinch/kdeconnect-android/tree/notifications-connect-status))
- Added ability to send SMS short code to clipboard. (branch: [send-sms-short-code](https://github.com/Ashinch/kdeconnect-android/tree/send-sms-short-code))
> The SMS short code extraction function modifies the `SmsCodeUtils.java` source file in the [tianma8023/SmsCode](https://github.com/tianma8023/SmsCode) repository.

---
KDE Connect 是一个多平台的应用程序，允许您的设备进行通信（例如：您的手机和您的电脑）。

此仓库是个人修改版本，额外的功能:

- 添加了连接丢失或重连时发送通知的功能。 (branch: [notifications-connect-status](https://github.com/Ashinch/kdeconnect-android/tree/notifications-connect-status))

- 添加自动提取并发送短信验证码至电脑剪贴板功能。 (branch: [send-sms-short-code](https://github.com/Ashinch/kdeconnect-android/tree/send-sms-short-code))

  ![send-sms-short-code.gif](https://github.com/Ashinch/kdeconnect-android/blob/master/screenshots/send-sms-short-code.gif?raw=true)
> 验证码提取功能修改于 [tianma8023/SmsCode](https://github.com/tianma8023/SmsCode) 仓库中的 `SmsCodeUtils.java` 源文件。


## (Some) Features
- **Shared clipboard**: copy and paste between your phone and your computer (or any other device).
- **Notification sync**: Read and reply to your Android notifications from the desktop.
- **Share files and URLs** instantly from one device to another.
- **Multimedia remote control**: Use your phone as a remote for Linux media players.
- **Virtual touchpad**: Use your phone screen as your computer's touchpad and keyboard.

All this without wires, over the already existing WiFi network, and using TLS encryption.

## About this app

This is a native Android port of the KDE Connect Qt app. You will find a more complete readme about KDE Connect [here](https://invent.kde.org/network/kdeconnect-kde/).

## How to install this app

You can install this app from the [Play Store](https://play.google.com/store/apps/details?id=org.kde.kdeconnect_tp) as well as [F-Droid](https://f-droid.org/repository/browse/?fdid=org.kde.kdeconnect_tp). Note you will also need to install the [desktop app](https://invent.kde.org/network/kdeconnect-kde) for it to work.

## Contributing

A lot of useful information, including how to get started working on KDE Connect and how to connect with the current developers, is on our [KDE Community Wiki page](https://community.kde.org/KDEConnect)

For bug reporting, please use [KDE's Bugzilla](https://bugs.kde.org). Please do not use the issue tracker in GitLab since we want to keep everything in one place.

To contribute patches, use [KDE Connect's Gitlab](https://invent.kde.org/kde/kdeconnect-android/).
On Gitlab (as well as on our [old Phabricator](https://phabricator.kde.org/tag/kde_connect/)) you can find a task list with stuff to do and links to other relevant resources.
It is a good idea to also subscribe to the [KDE Connect mailing list](https://mail.kde.org/mailman/listinfo/kdeconnect).

Please know that all translations for all KDE apps are handled by the [localization team](https://l10n.kde.org/). If you would like to submit a translation, that should be done by working with the proper team for that language.

## License
[GNU GPL v2](https://www.gnu.org/licenses/gpl-2.0.html) and [GNU GPL v3](https://www.gnu.org/licenses/gpl-3.0.html)

If you are reading this from Github, you should know that this is just a mirror of the [KDE Project repo](https://invent.kde.org/network/kdeconnect-android/).
