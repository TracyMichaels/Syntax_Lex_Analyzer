project contents:
MainDriver.java		- contains main class to test run lexical and syntactic analyzers
SyntaxAnalyzer.java 	- this class syntatically analyzes the given file input and prints to console whether
			  the file is syntactically valid or not
			- also contains rules for determining syntax
Tokenizer.java		- lexically analyzes string from input file, determins what each token is and stores its
			  value in a list to be used by SyntaxAnalyzer
SymbolTable.java	- contains all special symbol/lexical definitions and their token integer value as well 
			  as a hash map of token to symbol as well as regex for pattern matching to integer literals
			  float literals and variables
testFile_Valid.txt	- example of a valid file for input, produces "Valid Syntax" in console
testFile_Invalid.txt	- example of invalid syntax from file input, produces "Invalid Syntax" in console

*********
to run project use MainDriver.java
file input may be done as command line argument or if no argument presented use will be asked to input file path
of file they wish to analyze


/*
    rules:
    
    <PROGRAM>	--> <PROGRAM>   --> "{" <START> "}"
    <START>   	--> <STMT> ";" {<STMT> ";"}
    <STMT>     	--> <EXPR> {"=" <EXPR>}
    <EXPR>      --> <TERM> {("==", "!=", "<", ">") <TERM>}
    <TERM>      --> <FACTOR> {("+", "-") <FACTOR>}
    <FACTOR>    --> <ID> {("*", "/", "%") <ID>}
    <ID>        --> var | int | float | "(" <BEGIN> ")"    
*/

/*
    valid lexemes as defined by language
    int literal     =   \d+
    float literal   =   \d+.\d* | \d*.\d+
    variable        =   \w+[\w\d]*
    plus sign       =   +
    minus sign      =   -
    multiply sign   =   *
    division sign   =   /
    modulous sign   =   %
    less than op    =   <
    graeter than op =   >
    equal to op     =   ==
    not equal to op =   !=
    assignment op   =   =
    open parenthesis=   (
    close paren     =   )
    end of statement=   ;     
    begin program   =   {
    end program     =   }	          
    everything else =   undefined     
*/
