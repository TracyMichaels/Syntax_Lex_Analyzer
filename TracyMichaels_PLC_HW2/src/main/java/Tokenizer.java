import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tracy
 */
public class Tokenizer {
    /***************************************************************************
    this class lexically analyzes given string from input file
    ***************************************************************************/
    
    private final String input;

    private final List<Integer> tokens;
    
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
    everything else =   undefined 
    
    */
    public Tokenizer(String input){
        this.input = input;
        this.tokens = new ArrayList<>();
    }
    
    //logic loop for separating lexemes and converting into tokens
    public void tokenize(){
        int charIndex = 0;
        //used to store string of consecutive non-special characters to be tested
        //by regex to determine their quality
        String temp = "";
        
        while(charIndex < input.length()){
            //consume white space
            while(Character.isWhitespace(input.charAt(charIndex))) {
                charIndex++;
            }
            
            //if current character is in symbol map and
            if(SymbolTable.SYMBOL_MAP.containsKey(input.charAt(charIndex))){
                //if temp is not empty compare to regex for var or lit
                if(!temp.equals("")){
                    if(SymbolTable.INT_PATTERN.matcher(temp).matches()){
                        tokens.add(SymbolTable.INT_LIT);
                    }else if(SymbolTable.FLOAT_PATTERN.matcher(temp).matches()){
                        tokens.add(SymbolTable.FLOAT_LIT);
                    }else if(SymbolTable.VAR_PATTERN.matcher(temp).matches()){
                        tokens.add(SymbolTable.VAR);
                    } else {
                        tokens.add(SymbolTable.UNDEFINED);                        
                    }
                    //reset temp
                    temp = "";
                    continue;
                }
                
                //check for edge case neg equality symbol
                if(input.charAt(charIndex) == SymbolTable.NOT_EQUAL_START_SIGN){
                    if(input.charAt(charIndex + 1) == SymbolTable.ASSIGN_SIGN){
                        tokens.add(SymbolTable.NOT_EQUAL);
                        charIndex += 2;
                        continue;
                    }
                    tokens.add(SymbolTable.UNDEFINED);
                    charIndex++;
                    continue;
                }
                
                //check edge case equality symbol
                if(input.charAt(charIndex) == SymbolTable.ASSIGN_SIGN
                   && input.charAt(charIndex + 1) == SymbolTable.ASSIGN_SIGN){
                    tokens.add(SymbolTable.EQUAL);
                    charIndex += 2;
                    continue;
                }
                
                //place special symbol in tokens
                tokens.add(SymbolTable.SYMBOL_MAP.get(input.charAt(charIndex)));
                charIndex++;
                continue;
            }
            
            //if curr char is not in map add character to temp string
            //to evaluate if it is a var or lit at next special symbol
            temp += input.charAt(charIndex);
            charIndex++;
        }        
    }
    
    public List getTokens(){
        return this.tokens;
    }
}
