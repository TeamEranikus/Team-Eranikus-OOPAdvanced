package escape.code.models;

import escape.code.enums.Item;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable{
    private String name;
    private String password;
    private Long id;
    private Item item;
    private int level;
    private PuzzleRectangle puzzleRectangle;

    @Id
    @GeneratedValue()
    public Long getId() {
        return id;
    }

    public User(){
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puzzle_rectangle", nullable = false)
    public PuzzleRectangle getPuzzleRectangle() {
        return this.puzzleRectangle;
    }

    public void setPuzzleRectangle(PuzzleRectangle puzzleRectangle) {
        this.puzzleRectangle = puzzleRectangle;
    }
}
