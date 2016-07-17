package escape.code.enums;

public enum Item {
    KEY(true),
    BOOK(true),
    NONE(false);

    private boolean hasItem;

    Item(boolean hasItem){
        this.hasItem = hasItem;
    }

    public boolean hasItem(){
        return this.hasItem;
    }
}
