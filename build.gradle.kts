plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "com.example"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

application {
    // Ex. "src/main/java/game/DeckManager.java" becomes "game.Deckmanager"
    mainClass.set("main.Main")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

javafx {
    version = "27.0.1.1"
    modules = listOf(
        "javafx.controls", "javafx.fxml", "javafx.graphics",
        "javafx.media", "javafx.web", "javafx.swing",
    )
}



tasks.test {
    useJUnitPlatform()
}