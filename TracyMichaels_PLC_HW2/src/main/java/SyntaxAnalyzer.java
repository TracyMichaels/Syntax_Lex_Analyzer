
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
        if(tokens.get(currTokenIndex) != SymbolTable.BEGIN_PROGRAM) System.exit(exitWithErr());
        currTokenIndex++;
        START();
        System.exit((tokens.get(currTokenIndex) == SymbolTable.END_PROGRAM) ? exitWithoutErr() : exitWithErr());
    }
    
    //<START>   --> <STMT> ";" {<STMT> ";"}
    private void START(){
        
        STMT();
        while(tokens.get(currTokenIndex) == SymbolTable.END_STMT){
            getNextToken();
            if(tokens.get(currTokenIndex) == SymbolTable.END_PROGRAM) return;
            STMT();
            
        }
        if(tokens.get(currTokenIndex) != SymbolTable.END_STMT) System.exit(exitWithErr());
        currTokenIndex++;
    }    
    
    //<STMT>     --> <EXPR> {"=" <EXPR>}
    private void STMT(){
        EXPR();
        while(tokens.get(currTokenIndex) == SymbolTable.ASSIGN){
            getNextToken();
            EXPR();
        }        
    }
    
    //<EXPR>      --> <TERM> {("==", "!=", "<", ">") <TERM>}
    private void EXPR(){
        TERM();
        while( tokens.get(currTokenIndex) == SymbolTable.EQUAL
           |tokens.get(currTokenIndex) == SymbolTable.NOT_EQUAL
           |tokens.get(currTokenIndex) == SymbolTable.LESS_THAN
           |tokens.get(currTokenIndex) == SymbolTable.GREATER_THAN){
            
            getNextToken();
            TERM();            
        }
    }
    
    //<TERM>      --> <FACTOR> {("+", "-") <FACTOR>}
    private void TERM(){
        FACTOR();
        while( tokens.get(currTokenIndex) == SymbolTable.ADD_OP
           |tokens.get(currTokenIndex) == SymbolTable.SUB_OP){
            
            getNextToken();
            FACTOR();
        }
    }
    
    //<FACTOR>    --> <ID> {("*", "/", "%") <ID>}
    private void FACTOR(){
        ID();
        while( tokens.get(currTokenIndex) == SymbolTable.MUL_OP
           |tokens.get(currTokenIndex) == SymbolTable.DIV_OP
           |tokens.get(currTokenIndex) == SymbolTable.MOD_OP){
            
            getNextToken();
            ID();
        }
    }
    
    //<ID>        --> var | int | float | "(" <BEGIN> ")"
    private void ID(){
        switch(tokens.get(currTokenIndex)){
            case SymbolTable.INT_LIT:
                getNextToken();
                break;
            case SymbolTable.FLOAT_LIT:
                getNextToken();
                break;
            case SymbolTable.VAR:
                getNextToken();
                break;
            case SymbolTable.OPEN_PAREN:
                getNextToken();
                STMT();
                if(tokens.get(currTokenIndex) != SymbolTable.CLOSE_PAREN) {
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
        return 0;
    }
}
