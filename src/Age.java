public enum Age {
    CHILD, ADULT;

    @Override
    public String toString() {
        return switch (this) {
            case CHILD -> "взрослые";
            case ADULT -> "дети";
        };
    }
}