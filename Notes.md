#Serenity Project
Serenity BDD is an open source library that aims to make the idea of living documentation a reality.

Here is the [link](https://serenity-bdd.github.io/theserenitybook/latest/index.html) for simple documentation.

###Steps to Create Project
1.Create a maven project called `B22SerenityProject`

2.under `pom.xml`
1.add below property section
```xml 
<properties>
<maven.compiler.source>8</maven.compiler.source>
<maven.compiler.target>8</maven.compiler.target>
<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>
```
3.Add dependencies
```xml
<!--        This is for base support for anything we do with serenity-->
        <dependency>
            <groupId>net.serenity-bdd</groupId>
            <artifactId>serenity-core</artifactId>
            <version>2.4.4</version>
        </dependency>
<!--        this is the dependency that wrap up rest assured with additional serenity support-->
        <dependency>
            <groupId>net.serenity-bdd</groupId>
            <artifactId>serenity-rest-assured</artifactId>
            <version>2.4.4</version>
        </dependency>

        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.7.1</version>
            <scope>test</scope>
        </dependency>
        <!--Junit 5 support for serenity -->
        <dependency>
            <groupId>io.github.fabianlinz</groupId>
            <artifactId>serenity-junit5</artifactId>
            <version>1.6.0</version>
        </dependency>

```

4.Add Build plugins
```xml
  <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>8</source>
                    <target>8</target>
                </configuration>
            </plugin>
            <!--            This is where the report is being generated after the test run -->
            <plugin>
                <groupId>net.serenity-bdd.maven.plugins</groupId>
                <artifactId>serenity-maven-plugin</artifactId>
                <version>2.4.4</version>
                <executions>
                    <execution>
                        <id>serenity-reports</id>
                        <phase>post-integration-test</phase>
                        <goals>
                            <goal>aggregate</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <!--         We want to run all the tests then generate one report -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.0.0-M5</version>
                <configuration>
                    <testFailureIgnore>true</testFailureIgnore>
                </configuration>
            </plugin>
        </plugins>
    </build>
```
5.Create a package called `b22` under `src/test/java`
1. under b22 create `spartan` and under spartan create `admin` packages

2. create a class called `SpartanAdminGetTest`

6.Create regular @Test rest api class `getAllSpartan` and send a request.

7.This is just regular test, in order to make it recognized by serenity reports
* add annotation class level : `@SerenityTest`
* it is coming from `import net.serenitybdd.junit5.SerenityTest;
  `

8. Add a properties file with exact name `serenity.properties`
   right under project level
* add following lines to properties file
```properties
serenity.project.name=B22 API Report
serenity.test.root=b22
```
9.In order to generate serenity report, we need to use maven goal
* if you are using command line: `mvn clean verify`  cmd+enter or ctrl+enter if you dont have maven installed locally
* if you are using IntelliJ buttons;
    * first click on clean then click on verify
* your report will be generated under target as HTML Report

10.Thi sis for serenity to pick up log and eliminate the warning
```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-simple</artifactId>
    <version>1.7.30</version>
</dependency>
```
11. We were able to generate test report, however there are no details about the request and response.
    In order to see the details then we need to use the `given() when() then()` methods coming from Serenity.
    * Instead of importing rest assured `given` import,use below
        * `import static net.serenitybdd.rest.SerenityRest.given;`
    * From this point on, all details will be picked up by Serenity report, you will see **Rest Query** button on the individual tests

12. The way that assert the response and show it as a steps in Serenity report is using `Ensure` class (from `import net.serenitybdd.rest.Ensure;`)
```java
        Ensure.that("Status code is 200",validatableResponse -> validatableResponse.statusCode(201) );
        Ensure.that("Content-type is JSON",vRes -> vRes.contentType(ContentType.JSON));
        Ensure.that("Id is 15", vRes -> vRes.body("id",is(15)));
```