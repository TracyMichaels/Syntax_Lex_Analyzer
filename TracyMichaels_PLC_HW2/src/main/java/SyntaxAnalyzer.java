import java.util.List;

/**
 *
 * @author Tracy
 */
public class SyntaxAnalyzer {
    /***************************************************************************
    This Class syntactically analyzes whether the given string input from file is
    valid or not and displays such in console
    ***************************************************************************/
    
    /*
    rules:
    
    <PROGRAM>   --> "{" <START> "}"
    <START>     --> <STMT> ";" {<STMT> ";"}
    <STMT>      --> <EXPR> {"=" <EXPR>}
    <EXPR>      --> <TERM> {("==", "!=", "<", ">") <TERM>}
    <TERM>      --> <FACTOR> {("+", "-") <FACTOR>}
    <FACTOR>    --> <ID> {("*", "/", "%") <ID>}
    <ID>        --> var | int | float | "(" <BEGIN> ")"
    */
    
    private final List<Integer> tokens;
    private int currTokenIndex = 0;
    
    public SyntaxAnalyzer(List tokens){
        this.tokens = tokens;
    }
    
    public void analyze(){
        PROGRAM();
    }
    
    //<PROGRAM> --> "{" <START> "}"
    private void PROGRAM(){
        if(tokens.get(currTokenIndex) != SymbolChart.BEGIN_BLOCK) System.exit(exitWithErr());
        currTokenIndex++;
        START();
        //check if last token
        if(currTokenIndex + 1 != tokens.size()) return;
        System.exit((tokens.get(currTokenIndex) == SymbolChart.END_BLOCK) ? exitWithoutErr() : exitWithErr());
    }
    
    //<START>   --> <STMT> ";" {<STMT> ";"}
    private void START(){
        checkForLoopKeyword();
        STMT();
        while(tokens.get(currTokenIndex) == SymbolChart.END_STMT){
            getNextToken();
            if(tokens.get(currTokenIndex) == SymbolChart.END_BLOCK) return;
            STMT();
            

        }
        if(tokens.get(currTokenIndex) != SymbolChart.END_STMT) System.exit(exitWithErr());
        currTokenIndex++;
    }    
    
    //<STMT>     --> <EXPR> {"=" <EXPR>}
    private void STMT(){
        EXPR();
        while(tokens.get(currTokenIndex) == SymbolChart.ASSIGN){
            getNextToken();
            EXPR();
        }        
    }
    
    //<EXPR>      --> <TERM> {("==", "!=", "<", ">") <TERM>}
    private void EXPR(){
        TERM();
        while( tokens.get(currTokenIndex) == SymbolChart.EQUAL
           |tokens.get(currTokenIndex) == SymbolChart.NOT_EQUAL
           |tokens.get(currTokenIndex) == SymbolChart.LESS_THAN
           |tokens.get(currTokenIndex) == SymbolChart.GREATER_THAN){
            
            getNextToken();
            TERM();            
        }
    }
    
    //<TERM>      --> <FACTOR> {("+", "-") <FACTOR>}
    private void TERM(){
        FACTOR();
        while( tokens.get(currTokenIndex) == SymbolChart.ADD_OP
           |tokens.get(currTokenIndex) == SymbolChart.SUB_OP){
            
            getNextToken();
            FACTOR();
        }
    }
    
    //<FACTOR>    --> <ID> {("*", "/", "%") <ID>}
    private void FACTOR(){
        ID();
        while( tokens.get(currTokenIndex) == SymbolChart.MUL_OP
           |tokens.get(currTokenIndex) == SymbolChart.DIV_OP
           |tokens.get(currTokenIndex) == SymbolChart.MOD_OP){
            
            getNextToken();
            ID();
        }
    }
    
    //<ID>        --> var | int | float | "(" <BEGIN> ")"
    private void ID(){
        switch(tokens.get(currTokenIndex)){
            case SymbolChart.INT_LIT:
                getNextToken();
                break;
            case SymbolChart.FLOAT_LIT:
                getNextToken();
                break;
            case SymbolChart.VAR:
                getNextToken();
                break;
            case SymbolChart.OPEN_PAREN:
                getNextToken();
                STMT();
                if(tokens.get(currTokenIndex) != SymbolChart.CLOSE_PAREN) {
                    System.exit(exitWithErr());
                } else {
                    getNextToken();
                }
                break;
            default:
                System.exit(exitWithErr());               
        }
    }
    
    private void getNextToken(){
        currTokenIndex++;
        if(currTokenIndex >= tokens.size()) System.exit(exitWithErr());
    }
    
    private int exitWithoutErr(){
        System.out.println("Valid Syntax");
        return 0;
    }
    
    private int exitWithErr(){
        System.out.println("Invalid Syntax");
        return 1;
    }
    
    //subprogram for while, do-while, and if-else statements
    private void loopKeywordFound(){
        switch(tokens.get(currTokenIndex)){
            //'if (<STMT>) { <STMT_LIST> (or PROGRAM in this case)} else { <STMT_LIST> }
            case SymbolChart.IF_KEY:
                getNextToken();
                //next part would need to be a '( <STMT> )'
                //ID() is already set up to handle that syntax
                //so this checks the next token is a '(' and if it's not then
                //program exits with error
                if(tokens.get(currTokenIndex) == SymbolChart.OPEN_PAREN){                    
                    ID();
                } else {
                    System.exit(exitWithErr());
                }
                //if syntax is correct then ID() exits and next needs to be
                //'{ <STMT_LIST>}' and PROGRAM() is already set up to handle that syntax
                //had to modify to check if it was at the end of the program or not 
                //so that it did not exit early from the turnary statement
                PROGRAM();
                //check for else, if found get check statement list
                if(tokens.get(currTokenIndex).equals(SymbolChart.ELSE_KEY)) PROGRAM();
                break;
            //'while ( <STMT> ) {<STMT_LIST>( or <PROGRAM> in this case)}
            case SymbolChart.WHILE_KEY:                
                getNextToken();
                //ID() to handle '(<STMT>)
                if(tokens.get(currTokenIndex) == SymbolChart.OPEN_PAREN){                    
                    ID();
                } else {
                    System.exit(exitWithErr());
                }                
                // PROGRAM to handle '{ <STMT_LIST>}'
                PROGRAM();                
                break;
            // 'do { <STMT_LIST> } while (<STMT>)'   
            case SymbolChart.DO_KEY:
                getNextToken();
                //to handle '{ <STMT_LIST> }'
                PROGRAM(); 
                //check for while keyword
                if(tokens.get(currTokenIndex).equals(SymbolChart.WHILE_KEY)){
                    getNextToken();
                    //to handle '(<STMT>)'
                    if(tokens.get(currTokenIndex) == SymbolChart.OPEN_PAREN){                    
                        ID();
                    } else {
                        System.exit(exitWithErr());
                    }
                } else {
                    System.exit(exitWithErr());
                }                
                break;
            default:
                System.exit(exitWithErr());
                
        }
    }

    private void checkForLoopKeyword() {
        if(SymbolChart.KEYWORD_MAP.containsValue(tokens.get(currTokenIndex))){
            switch(tokens.get(currTokenIndex)){
                case SymbolChart.IF_KEY:
                case SymbolChart.WHILE_KEY:
                case SymbolChart.DO_KEY:
                    loopKeywordFound();
                    break;
                default:
                    break;
            }
        }
    }
}
