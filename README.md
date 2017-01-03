# au-software-design-fall-2016

Primitive shell(like bash) with almost no future

To make one own shell you can clone this repository and run `mvn clean install` in terminal

In first approximation supports `echo $INPUT`, `cat $FILE`, `wc $FILE`, `pwd` and `exit` commands with strong(`'`) and weak quotes(`"`)
 
Also supports some sort of environment variables, namely you can assign a value to a variable amd get a value from variable via `$` operator. 
 
Shell has no data types, everything is string 

__UPD 23.10.16__ NEW AND ALLMIGHTY SHELL! NOW WITH GREP! FREE! NO SMS! NO SIGN UP!

Also supports `grep` utility with keys:
   * `-i` for case-insensitive matching
   * `-w` for selecting lines that contain matches, which forms only whole words  
   * `-A n` for printing n lines after matched line(empty lines are __not__ taken into count)
    
This project on pivotal tracker:
[link](https://www.pivotaltracker.com/n/projects/1870057)

## Note for patterns hw

Used patterns:
- all commands are implemented with Command pattern
- Shell itself is a Singleton
- Shell uses CommandProvider which is a small Factory
- ExternalCmd is an Adapter

4 patterns for such small shell(however, i had to get rid of Builder =( )


