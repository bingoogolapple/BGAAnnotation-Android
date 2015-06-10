:heartpulse:BGAAnnotation-Android v1.0.0:heartpulse:
====

Android自动注入注解框架

主要功能：activity、fragment、dialog、自定义组合控件中layout和view的自动注入

PS：很多注解框架都有这个功能，它们同时还包含了数据库以及网络请求等注解，但是有些项目中根本用不到数据库和网络请求，如果这时使用那些注解框架，处女座就要:angry:了

#### 效果图
![Image of BGAAnnotationDemo](http://bingoshare.u.qiniudn.com/LoonDemo3.gif)
![Image of BGAAnnotationDemo](http://bingoshare.u.qiniudn.com/LoonDemo1.gif)
![Image of BGAAnnotationDemo](http://bingoshare.u.qiniudn.com/LoonDemo2.gif)

>Gradle

```groovy
dependencies {
    compile 'cn.bingoogolapple:bga-annotation:1.0.0@aar'
}
```

##### 详细用法请查看[demo](https://github.com/bingoogolapple/BGAAnnotation-Android/tree/master/demo):feet:

## License

    Copyright 2015 bingoogolapple

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
