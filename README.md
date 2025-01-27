# LiveRecorder Plugin

## 简介

LiveRecorder 是一款 Minecraft 插件，旨在为玩家提供实时直播功能。通过该插件，你可以控制一个目标玩家进行直播，并根据设定的时间自动切换直播目标。

## 功能

- 设置目标玩家为直播对象
- 配置每个玩家的直播时间
- 自动跟随活跃玩家并生成直播画面

## 安装

1. 下载并安装插件到服务器的 `plugins/` 目录。
2. 重启服务器或运行 `/reload` 命令。
3. 根据需要配置 `config.yml` 文件。

## 使用指令

- `/live target <name>`：设置目标玩家为直播对象
- `/live time <second>`：设置每个玩家的直播时间
- `/live recorder <name>`：设置目标玩家为直播录制者
- `/live reset`：重设直播时间，自动切换直播目标
- `/live reload`：重载插件配置文件

## 配置文件

插件的配置文件位于 `resources/config.yml`，你可以修改其中的参数来调整插件的行为。

## 贡献

欢迎提交更多功能建议和反馈！你可以通过 GitHub 提交 Issues 或 Pull Requests。
