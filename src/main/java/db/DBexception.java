package db;

public class DBexception extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DBexception(String msg){
        super(msg);
    }
}
