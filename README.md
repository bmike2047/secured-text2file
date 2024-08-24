Secured Text 2 File
--
![](assets/images/logo1.png)
### Code repository
<a href="https://github.com/bmike2047/secured-text2file">https://github.com/bmike2047/secured-text2file</a>

### Description
Secure store any text to an AES256 hidden zip file. (binaries provided also, see end of this file) <br/>
Can be used as a password manager.<br/>
Output file defaults to info.bin in the current directory. This can be changed if needed. <br/>
While there are a lot of self-extracting archive managers with encryption, this little 
program has the advantage that it extracts and display the content of the encrypted file in his little frame without the need to use an external text editor.<br/>
This eliminates the step to delete the raw sensible file after visualization/decryption that can lead to other security problems, like forgetting to empty the recycle bin.<br/>

E.g: Encrypt your drive with bitlocker then use this program to add an extra security layer for your important text/passwords.

### Features summary
* No need for external editor to view your decrypted text 
*  No need to delete your decrypted text after you've read it
*  Leaves no footprint like temp files
*  No network connections

### Usage
Requires JDK 17<br/>
To run the project use the following command:<br/>
```
./gradlew clean run
```
For a java distribution run the command below and check folder: ./build/install/secured-text2file/bin<br/>
```
./gradlew clean installDist
```
For native OS executable run the command below and check folder: ./build/image/bin
```
sudo apt-get install binutils //only for linux
./gradlew clean jlink 
```

> [!TIP]
> To skip building and run directly on your computer install JDK17+ and run the script file inside folder:<br/>
> ***./dist/your-OS/secured-text2file/bin/***

> [!TIP]
> I case you have older JDK version than JDK 17 and you are using IntelliJIDEA as IDE make sure Gradle JVM is set to JDK 17 in:<br/>
> File | Settings | Build, Execution, Deployment | Build Tools | Gradle  -> Gradle JVM

### Screenshot
![](assets/images/screen1.png)
