pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
val mavenUser: String by settings
val mavenToken: String by settings

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/persado/eapi-mobile-sdk")
            credentials {
                username = mavenUser
                password = mavenToken
            }
        }
    }
}

rootProject.name = "Persado SDK Example"
include(":app")
