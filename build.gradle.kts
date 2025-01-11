plugins { 
  java
  eclipse
}

allprojects {
   group = "it.discovery"
}

subprojects {  
   apply(plugin = "java")

   java.sourceCompatibility = JavaVersion.VERSION_23
   java.targetCompatibility = JavaVersion.VERSION_23

   repositories {
      mavenCentral()
   }
   
   dependencies {
        implementation("jakarta.annotation:jakarta.annotation-api:3.0.0")

        compileOnly("org.projectlombok:lombok:1.18.36")
        annotationProcessor("org.projectlombok:lombok:1.18.36")
   } 
   tasks.withType<JavaCompile>(){
     options.compilerArgs.add("-parameters")
   }
}

