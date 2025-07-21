## hs-strategy-plugin-template

此项目为[Hearthstone-Script](https://github.com/xjw580/Hearthstone-Script)的策略插件模板




## 协议

本项目遵循 **[GPL3.0开源协议](LICENSE)** 及 **[禁止商用附加协议](LICENSE1)**



### 前置

1. 阅读 [hs-script-card-sdk ](https://github.com/xjw580/hs-script-card-sdk)项目和 [hs-script-strategy-sdk](https://github.com/xjw580/hs-script-strategy-sdk) 项目，插件的开发基于提供的这两个SDK，对SDK的使用参考[TemplateStrategyDeck](src/main/kotlin/club/xiaojiawei/strategyplugintemplate/TemplateStrategyDeck.kt)https://github.com/xjw580/hs-script-base-card-plugin)
2. 阅读 [hs-script-base-strategy-plugin](https://github.com/xjw580/hs-script-base-strategy-plugin) 项目



### 策略插件开发步骤

1. 通过此仓库模板创建一个新的仓库
2. 修改新仓库内的 [TemplatePlugin](src\main\kotlin\club\xiaojiawei\strategyplugintemplate\TemplatePlugin.kt)  和  [TemplateStrategyDeck](src\main\kotlin\club\xiaojiawei\strategyplugintemplate\TemplateStrategyDeck.kt)
3. `TemplatePlugin`是插件的配置类，在  [club.xiaojiawei.hsscriptstrategysdk.StrategyPlugin](src\main\resources\META-INF\services\club.xiaojiawei.hsscriptstrategysdk.StrategyPlugin) 中注册，注册方式为全限定类名，只有注册了才能被识别
4. `TemplateStrategyDeck`是策略类，在  [club.xiaojiawei.hsscriptstrategysdk.DeckStrategy](src\main\resources\META-INF\services\club.xiaojiawei.hsscriptstrategysdk.DeckStrategy)  中注册，注册方式为全限定类名，只有注册了才能被识别
5. 修改完后可以通过idea打包或执行`mvn clean package`
6. 将生成的jar包放入软件根目录的plugin文件夹下



### 调试

1. 创建完新仓库后复制仓库地址，然后在克隆的[Hearthstone-Script](https://github.com/xjw580/Hearthstone-Script)根目录下执行

   ```tex
   git submodule add 项目clone地址 user-strategy-plugins/你的项目名
   ```

2. 等clone完毕，在`Hearthstone-Script`的`pom.xml`文件中的`modules`标签里添加你的模块

3. 在`hs-script-app`的`pom.xml`文件中的`dependencies`标签里添加你的插件

4. 这样就不用打成jar包使用了测试了

5. 如果想让更多人知道你的插件，可以在添加git子模块后向[Hearthstone-Script](https://github.com/xjw580/Hearthstone-Script)提PR

