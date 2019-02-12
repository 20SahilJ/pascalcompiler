# pascalcompiler
This repository contains the code, research paper, and a sample input file associated with a working compiler/interpreter for the SIMPLE language. 
The ast file contains the classes that represent the different components of the compiler (including an if statement, for loop, variable, etc). 
The environment file helps to set up the appropriate environment in which to create and store variables (this file helps to handle for variable scope i.e. when there are both local and global variables present).
A scanner is used to pre-process the input SIMPLE-language code, while the parser is used to interpret the code and perform the appropriate output. 

Please read through the research paper included to gain a sense of the grammar of the compiler as well as the specifics of the interpreter. A sample SIMPLE language input file has been provided. In order to run it, download the code in the repository, and run the main method in the Parser class on the input file.
