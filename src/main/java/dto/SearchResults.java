package dto;

import java.util.List;

public class SearchResults {
    public String cursorToken;
    public Long originalStatementCount;
    public List<Statements> statements;
}
