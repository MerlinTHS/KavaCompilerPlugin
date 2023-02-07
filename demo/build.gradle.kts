import com.mths.kava.KavaMode

plugins {
    kotlin("multiplatform")
    id("com.mths.kava")
}

repositories {
    mavenCentral()
    mavenLocal()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
}

kava {
    enabled = true
    mode = KavaMode.Explicit
}