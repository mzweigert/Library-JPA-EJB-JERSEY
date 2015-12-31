package com.library.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "author.all", query = "Select a from Author a"),
        @NamedQuery(name = "author.bySurname", query = "Select a from Author a where a.surname = :surname")
})
@Table(name = "Author")
public class Author implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAuthor;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "author")
    @JsonIgnore
    private List<BooksAuthors> booksAuthors = null;

    public Author()
    {

    }

    public Author(String name, String surname)
    {
        this.name = name;
        this.surname = surname;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public long getIdAuthor()
    {
        return idAuthor;
    }

    public void setIdAuthor(long idAuthor)
    {
        this.idAuthor = idAuthor;
    }

    public List<BooksAuthors> getBooksAuthors() {
        return booksAuthors;
    }

    public void setBooksAuthors(List<BooksAuthors> booksAuthors) {
        this.booksAuthors = booksAuthors;
    }

    @Override
    public String toString()
    {
        return "Author{" +
                "idAuthor=" + idAuthor +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;

        Author author = (Author) o;

        if (getIdAuthor() != author.getIdAuthor()) return false;
        if (!getName().equals(author.getName())) return false;
        return (getSurname().equals(author.getSurname()));

    }

    @Override
    public int hashCode()
    {
        int result = (int) (getIdAuthor() ^ (getIdAuthor() >>> 32));
        result = 31 * result + getName().hashCode();
        result = 31 * result + getSurname().hashCode();
        result = 31 * result + getBooksAuthors().hashCode();
        return result;
    }
}
