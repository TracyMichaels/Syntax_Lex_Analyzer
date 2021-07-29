
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
        if(tokens.get(currTokenIndex) != SymbolChart.BEGIN_PROGRAM) System.exit(exitWithErr());
        currTokenIndex++;
        START();
        System.exit((tokens.get(currTokenIndex) == SymbolChart.END_PROGRAM) ? exitWithoutErr() : exitWithErr());
    }
    
    //<START>   --> <STMT> ";" {<STMT> ";"}
    private void START(){
        
        STMT();
        while(tokens.get(currTokenIndex) == SymbolChart.END_STMT){
            getNextToken();
            if(tokens.get(currTokenIndex) == SymbolChart.END_PROGRAM) return;
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
        return 0;
    }
}
