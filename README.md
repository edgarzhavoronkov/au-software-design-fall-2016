# au-software-design-fall-2016

Primitive shell(like bash) with almost no future

To make one own shell you can clone this repository and run `mvn clean install` in terminal

In first approximation supports `echo $INPUT`, `cat $FILE`, `wc $FILE`, `pwd` and `exit` commands with strong(`'`) and weak quotes(`"`)
 
Also supports some sort of environment variables, namely you can assign a value to a variable amd get a value from variable via `$` operator. 
 
Shell has no data types, everything is string 
