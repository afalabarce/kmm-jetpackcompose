import org.gradle.api.JavaVersion

object BuildVersion {
    object Environment {
        private const val majorVersion = 1
        private const val minorVersion = 0
        private const val bugfixVersion = 0

        const val libraryBasePackage = "dev.afalabarce.kmm.jetpackcompose"
        const val appVersionCode = majorVersion * 1000 + minorVersion * 100 + bugfixVersion
        const val appVersionName = "${majorVersion}.${minorVersion}.$bugfixVersion"
    }

    object Android {
        const val minSdkVersion = 24
        const val compileSdkVersion = 34

        const val jvmTarget = "17"
        val javaCompatibility = JavaVersion.VERSION_17
    }

    object MavenCentralPublish {
        object LibraryInfo {
            const val libraryName = "KMP JetpackCompose Library"
            const val libraryDescription =
                "Library with some Composables and utilities that targets Kotlin Multiplatform Environments"
            const val libraryUrl = "https://github.com/afalabarce/kmm-jetpackcompose"
            const val licenseType = "MIT"
            const val licenseUrl = "https://opensource.org/licenses/MIT"
            const val ScmUrl = "https://github.com/afalabarce/kmm-jetpackcompose"
        }

        object DeveloperInfo {
            val id = "afalabarce"
            val name = "Antonio Fdez Alabarce"
            val organization = "afalabarce@Dev"
            val organizationUrl = "https://afalabarce.dev"
        }
    }
}