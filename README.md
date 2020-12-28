# GitHub Repos

GitHub Repos is a sample Android app created using: 
* Kotlin
* MVVM
* Paging 3 library
* LiveData
* Navigation Component
* Data binding
* Kotlin Coroutines
* GitHub REST API

## Project description

This project displays a list of __GitHub repositories__ based on a default keyword, which are loaded onto a ``RecyclerView``, with an infinite scroll implemented through the Paging 3 library for a more comfortable browsing experience.

The user can type any other keyword to search for the content they need. When the list of repositories is displayed, the user can navigate to a detail fragment of any individual repo they choose, and this screen will display some additional information about each repo, including the __owner__, __stars__ and __forks__ received, in addition to a link to view the repository in full on __GitHub.com__.

## Other third-party libraries used in this project:
* [Retrofit 2](https://github.com/square/retrofit)
* [Moshi](https://github.com/square/moshi)
* [Glide](https://github.com/bumptech/glide)
* [Material components](https://github.com/material-components/material-components-android)
* [Stetho](https://github.com/facebookarchive/stetho)

## How to get an GitHub REST API key:
You'll need to get a GitHub REST API key for this application to work.

Go to [this link](https://docs.github.com/en/free-pro-team@latest/github/authenticating-to-github/creating-a-personal-access-token) and follow the guide to create your token, which will be associated to your GitHub account.

## License
[GNU General Public License v3](https://www.gnu.org/licenses/gpl-3.0.en.html)

Data provided by GitHub.com.