plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


tasks.test {
    useJUnitPlatform {
        includeTags("main")
        excludeTags("failed")
    }
}

//Добавление задач для запуска всех тестов после которых пишем текст что ран с тестами окончен

tasks.register("runAllTests") {
    dependsOn(tasks.test)
    finalizedBy("afterTests")
}

tasks.register("afterTests") {
    doLast {
        println("Test run is over")
    }
}

