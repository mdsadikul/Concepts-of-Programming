import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class CStatementTokenizer {

    // Set of C language keywords
    private static final Set<String> keywords = new HashSet<>();
    static {
        String[] keywordArray = {"int", "float", "double", "char", "if", "else", "for", "while", "return", "void", "main"};
        for (String keyword : keywordArray) {
            keywords.add(keyword);
        }
    }

    // Token categories
    enum TokenType {
        KEYWORD, IDENTIFIER, OPERATOR, NUMBER, UNKNOWN
    }

    // Token class
    static class Token {
        String lexeme;
        TokenType type;

        Token(String lexeme, TokenType type) {
            this.lexeme = lexeme;
            this.type = type;
        }

        @Override
        public String toString() {
            return "Lexeme: " + lexeme + ", Category: " + type;
        }
    }

    // Function to categorize token type
    private static TokenType getTokenType(String lexeme) {
        if (keywords.contains(lexeme)) return TokenType.KEYWORD;
        if (Character.isAlphabetic(lexeme.charAt(0))) return TokenType.IDENTIFIER;
        if (Character.isDigit(lexeme.charAt(0))) return TokenType.NUMBER;
        if (lexeme.equals("+") || lexeme.equals("-") || lexeme.equals("*") || lexeme.equals("/") || lexeme.equals("="))
            return TokenType.OPERATOR;
        return TokenType.UNKNOWN;
    }

    // Function to tokenize a statement
    private static void tokenize(String statement) {
        StringBuilder lexeme = new StringBuilder();
        for (int i = 0; i < statement.length(); i++) {
            char c = statement.charAt(i);

            if (Character.isWhitespace(c)) {
                if (lexeme.length() > 0) {
                    processToken(lexeme.toString());
                    lexeme.setLength(0);
                }
            }
            else if ("+-*/=(){};".indexOf(c) != -1) {  // Operators and delimiters
                if (lexeme.length() > 0) {
                    processToken(lexeme.toString());
                    lexeme.setLength(0);
                }
                processToken(String.valueOf(c));
            }
            else {
                lexeme.append(c);
            }
        }
        if (lexeme.length() > 0) {
            processToken(lexeme.toString());
        }
    }

    // Function to print a single token
    private static void processToken(String lexeme) {
        TokenType type = getTokenType(lexeme);
        Token token = new Token(lexeme, type);
        System.out.println(token);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a C statement: ");
        String statement = scanner.nextLine();
        scanner.close();

        tokenize(statement);
    }
}
