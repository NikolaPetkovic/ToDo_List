package classes;

public class Tag {
    private int id;
    private String color;
    private String name;

    public Tag(int id, String color, String name){
        this.id = id;
        this.color = color;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getShortName(){
        String shortName;
        if(name.length()>14){
            shortName = name.substring(0,13)+"..";
        }else{
            shortName = name;
        }
        return shortName;
    }

    public void setName(String name) {
        this.name = name;
    }
}