E:\JAVA\JAVA22\bin\java.exe -Dmaven.multiModuleProjectDirectory=E:\IdeaProjects\cfdownloadexample -Djansi.passthrough=true -Dmaven.home=C:\Users\Ming\.m2\wrapper\dists\apache-maven-3.9.9-bin\4nf9hui3q3djbarqar9g711ggc\apache-maven-3.9.9 -Dclassworlds.conf=C:\Users\Ming\.m2\wrapper\dists\apache-maven-3.9.9-bin\4nf9hui3q3djbarqar9g711ggc\apache-maven-3.9.9\bin\m2.conf "-Dmaven.ext.class.path=D:\JetBrains\IntelliJ IDEA 2024.2.1\plugins\maven\lib\maven-event-listener.jar" "-javaagent:D:\JetBrains\IntelliJ IDEA 2024.2.1\lib\idea_rt.jar=11798:D:\JetBrains\IntelliJ IDEA 2024.2.1\bin" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath C:\Users\Ming\.m2\wrapper\dists\apache-maven-3.9.9-bin\4nf9hui3q3djbarqar9g711ggc\apache-maven-3.9.9\boot\plexus-classworlds-2.8.0.jar;C:\Users\Ming\.m2\wrapper\dists\apache-maven-3.9.9-bin\4nf9hui3q3djbarqar9g711ggc\apache-maven-3.9.9\boot\plexus-classworlds.license org.codehaus.classworlds.Launcher -Didea.version=2024.2.1 -s D:\Maven\conf\setting.xml -Dmaven.repo.local=D:\Maven\repository package
[INFO] Scanning for projects...
[WARNING] 
[WARNING] Some problems were encountered while building the effective model for com.pandatech:downloadcf:jar:0.0.1-SNAPSHOT
[WARNING] 'dependencies.dependency.version' for org.projectlombok:lombok:jar is either LATEST or RELEASE (both of them are being deprecated) @ line 52, column 22
[WARNING] 
[WARNING] It is highly recommended to fix these problems because they threaten the stability of your build.
[WARNING] 
[WARNING] For this reason, future Maven versions might no longer support building such malformed projects.
[WARNING] 
[INFO] 
[INFO] ----------------------< com.pandatech:downloadcf >----------------------
[INFO] Building downloadcf 0.0.1-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ downloadcf ---
[INFO] Copying 1 resource from src\main\resources to target\classes
[INFO] Copying 6 resources from src\main\resources to target\classes
[INFO] 
[INFO] --- compiler:3.11.0:compile (default-compile) @ downloadcf ---
[INFO] Changes detected - recompiling the module! :source
[INFO] Compiling 66 source files with javac [debug release 17] to target\classes
[INFO] 由于在类路径中发现了一个或多个处理程序，因此启用了
  批注处理。未来发行版的 javac 可能会禁用批注处理，
  除非至少按名称指定了一个处理程序 (-processor)，
  或指定了搜索路径 (--processor-path, --processor-module-path)，
  或显式启用了批注处理 (-proc:only, -proc:full)。
  可使用 -Xlint:-options 隐藏此消息。
  可使用 -proc:none 禁用批注处理。
[INFO] -------------------------------------------------------------
[ERROR] COMPILATION ERROR : 
[INFO] -------------------------------------------------------------
[ERROR] /E:/IdeaProjects/cfdownloadexample/src/main/java/com/pandatech/downloadcf/adapter/YaliangBrandAdapter.java:[264,77] 找不到符号
  符号:   方法 getTemplateContent()
  位置: 类 com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs
[ERROR] /E:/IdeaProjects/cfdownloadexample/src/main/java/com/pandatech/downloadcf/adapter/YaliangBrandAdapter.java:[269,60] 找不到符号
  符号:   方法 getTemplateContent()
  位置: 类 com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs
[INFO] 2 errors 
[INFO] -------------------------------------------------------------
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  5.601 s
[INFO] Finished at: 2025-08-21T20:54:13+08:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.11.0:compile (default-compile) on project downloadcf: Compilation failure: Compilation failure: 
[ERROR] /E:/IdeaProjects/cfdownloadexample/src/main/java/com/pandatech/downloadcf/adapter/YaliangBrandAdapter.java:[264,77] 找不到符号
[ERROR]   符号:   方法 getTemplateContent()
[ERROR]   位置: 类 com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs
[ERROR] /E:/IdeaProjects/cfdownloadexample/src/main/java/com/pandatech/downloadcf/adapter/YaliangBrandAdapter.java:[269,60] 找不到符号
[ERROR]   符号:   方法 getTemplateContent()
[ERROR]   位置: 类 com.pandatech.downloadcf.entity.PrintTemplateDesignWithBLOBs
[ERROR] -> [Help 1]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] 

进程已结束，退出代码为 1

编译报错，请修复！