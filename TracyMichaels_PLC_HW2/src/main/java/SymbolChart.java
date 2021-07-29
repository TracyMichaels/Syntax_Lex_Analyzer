import java.util.HashMap;
import java.util.regex.Pattern;


/**
 *
 * @author Tracy
 */
public class SymbolChart {
    
    //regex patterns for int literal, float literal, and variables
    public final static Pattern INT_PATTERN = Pattern.compile("\\d+");
    public final static Pattern FLOAT_PATTERN = Pattern.compile("(\\d+\\.\\d*)|(\\d*\\.\\d+)");
    public final static Pattern VAR_PATTERN = Pattern.compile("\\w+[\\w\\d]*");
    
    //sign definitions, can be changed here to adjust how language writes
    //must be chars
    public final static char SUB_OP_SIGN = '-';
    public final static char MUL_OP_SIGN = '*';
    public final static char DIV_OP_SIGN = '/';
    public final static char MOD_OP_SIGN = '%';
    public final static char ADD_OP_SIGN = '+';
    public final static char LESS_THAN_SIGN = '<';
    public final static char GREATER_THAN_SIGN = '>';
    public final static char ASSIGN_SIGN = '=';
    public final static char OPEN_PAREN_SIGN = '(';
    public final static char CLOSE_PAREN_SIGN = ')';
    public final static char END_STMT_SIGN = ';';
    public final static char NOT_EQUAL_START_SIGN = '!';
    public final static char BEGIN_BLOCK_SIGN = '{';
    public final static char END_BLOCK_SIGN = '}';
    
    //combination of special characters can be used to create new meaning
    //defines equality sign as two assignment signs
    public final static String EQUAL_SIGN = "" + ASSIGN_SIGN + ASSIGN_SIGN;             //==
    //defines neg equality as not equal start sign followed by assignment sign
    public final static String NOT_EQUAL_SIGN = "" + NOT_EQUAL_START_SIGN + ASSIGN_SIGN; //!=
    
    //token representation, if needs to be changed, do here
    public final static int INT_LIT = 10;
    public final static int FLOAT_LIT = 20;
    public final static int VAR = 30;
    public final static int UNDEFINED = 99;
    public final static int ADD_OP = 100;
    public final static int SUB_OP = 110;
    public final static int MUL_OP = 120;
    public final static int DIV_OP = 130;
    public final static int MOD_OP = 140;
    public final static int LESS_THAN = 200;
    public final static int GREATER_THAN = 210;
    public final static int EQUAL = 220;
    public final static int NOT_EQUAL = 230;
    public final static int ASSIGN = 300;
    public final static int OPEN_PAREN = 400;
    public final static int CLOSE_PAREN = 410;
    public final static int END_STMT = 500;   
    public final static int BEGIN_BLOCK = 600; 
    public final static int END_BLOCK = 610; 
    
    
    
    //key words
    public static final String FOR_KEY_STR = "for";
    public static final String IF_KEY_STR = "for";
    public static final String ELSE_KEY_STR = "for";
    public static final String WHILE_KEY_STR = "for";
    public static final String DO_KEY_STR = "for";
    public static final String INT_KEY_STR = "for";
    public static final String FLOAT_KEY_STR = "for";
    public static final String SWITCH_KEY_STR = "for";
    public static final String CLASS_KEY_STR = "for";
    public static final String VOID_KEY_STR = "for";
    public static final String BOOL_KEY_STR = "for";
    
    //keyword tokens
    public static final int FOR_KEY = 701;
    public static final int IF_KEY = 702;
    public static final int ELSE_KEY = 703;
    public static final int WHILE_KEY = 704;
    public static final int DO_KEY = 705;
    public static final int INT_KEY = 706;
    public static final int FLOAT_KEY = 707;
    public static final int SWITCH_KEY = 708;
    public static final int CLASS_KEY = 709;
    public static final int VOID_KEY = 710;
    public static final int BOOL_KEY = 711;
    
    
    
    //map of single special symbol characters
    public static final HashMap<Character, Integer> SYMBOL_MAP = new HashMap<>();
    static {    
        SYMBOL_MAP.put(ADD_OP_SIGN, ADD_OP);
        SYMBOL_MAP.put(SUB_OP_SIGN, SUB_OP);
        SYMBOL_MAP.put(MUL_OP_SIGN, MUL_OP);
        SYMBOL_MAP.put(DIV_OP_SIGN, DIV_OP);
        SYMBOL_MAP.put(MOD_OP_SIGN, MOD_OP);
        SYMBOL_MAP.put(LESS_THAN_SIGN, LESS_THAN);
        SYMBOL_MAP.put(GREATER_THAN_SIGN, GREATER_THAN);
        SYMBOL_MAP.put(NOT_EQUAL_START_SIGN, null);
        SYMBOL_MAP.put(ASSIGN_SIGN, ASSIGN);
        SYMBOL_MAP.put(OPEN_PAREN_SIGN, OPEN_PAREN);
        SYMBOL_MAP.put(CLOSE_PAREN_SIGN, CLOSE_PAREN);
        SYMBOL_MAP.put(END_STMT_SIGN, END_STMT); 
        SYMBOL_MAP.put(BEGIN_BLOCK_SIGN, BEGIN_BLOCK); 
        SYMBOL_MAP.put(END_BLOCK_SIGN, END_BLOCK); 
        
    }
    
    public static final HashMap<String, Integer> KEYWORD_MAP = new HashMap<>();
    static {
        KEYWORD_MAP.put(FOR_KEY_STR, FOR_KEY);
        KEYWORD_MAP.put(IF_KEY_STR, IF_KEY);
        KEYWORD_MAP.put(ELSE_KEY_STR, ELSE_KEY);
        KEYWORD_MAP.put(WHILE_KEY_STR, WHILE_KEY);
        KEYWORD_MAP.put(DO_KEY_STR, DO_KEY);
        KEYWORD_MAP.put(INT_KEY_STR, INT_KEY);
        KEYWORD_MAP.put(FLOAT_KEY_STR, FLOAT_KEY);
        KEYWORD_MAP.put(SWITCH_KEY_STR, SWITCH_KEY);
        KEYWORD_MAP.put(CLASS_KEY_STR, CLASS_KEY);
        KEYWORD_MAP.put(VOID_KEY_STR, VOID_KEY);
        KEYWORD_MAP.put(BOOL_KEY_STR, BOOL_KEY);
    }
}
