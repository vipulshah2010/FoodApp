<h1 align="center">FoodApp</h1>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://github.com/vipulshah2010/FoodApp/actions"><img alt="Build Status" src="https://github.com/skydoves/Pokedex/workflows/Android%20CI/badge.svg"/></a> 
</p>

<p align="center">  
FoodApp is a small playground application built using MVVM architecture.<br>This project is primarily built with purpose of trying out new android tech stack.<br>
It showcase Usecase and Repository architecture pattern for loading data from network and persisting it in database.
</p>
</br>

<p align="center">
<img src="/art/cover.png"/>
</p>

## Library and Frameworks
- Minimum SDK level 21
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- Dagger-Hilt (alpha) for dependency injection.
- JetPack
  - LiveData - notify domain layer data to views.
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Room Persistence - construct a database using the abstract layer.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Repository pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Gson](https://github.com/google/gson) - JSON library for Kotlin and Java.
- [Picasso](https://square.github.io/picasso/)
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like ripple animation, cardView.

## Planned workflow:
- More unit test coverage
- Instrumentation test coverage
- Fix Shared element transition

## Architecture
FoodApp is based on MVVM architecture and a repository pattern.

<p align="center">
<img src="/art/architecture.png"/>
</p>

## Find this repository useful? :heart:
__[follow](https://twitter.com/_vipuls)__ me on twitter! 🤩

# License
```xml
Designed and developed by 2020 vipulshah2010 (Vipul Shah)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```