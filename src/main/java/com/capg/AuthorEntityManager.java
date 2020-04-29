package com.capg;

import javax.persistence.*;
import java.util.List;

public class AuthorEntityManager {
    EntityManagerFactory entityManagerFactory;

    public static void main(String[] args) {
        AuthorEntityManager authorEntityManager = new AuthorEntityManager();
        authorEntityManager.execute();
    }

    public void execute(){
        entityManagerFactory = Persistence.createEntityManagerFactory("author-mgt");
        Author author = new Author();
        author.setFirstName("Navdeep");
        author.setMiddleName("");
        author.setLastName("Tripathi");
        author.setPhoneNo(8090287569);
        author = addAuthor(author);
        printAuthor(author);
        //fetch All
        List<Author> list = fetchAll();
        for (Author currentAuthor:
             list) {
            printAuthor(currentAuthor);
        }
        //find by id
        Author findAuthor = findAuthorById(author.getAuthorId());
        printAuthor(findAuthor);

        //update author phone number
        author.setPhoneNo(9770808000L);
        Author updatedAuthor = updateAuthor(author);
        printAuthor(updatedAuthor);

        //delete author
        if(removeAuthorById(author.getAuthorId())){
            System.out.println("Author Removed Successfully");
        }else{
            System.out.println("Invalid Author id");
        }


    }

    public Author addAuthor(Author author){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(author);
        transaction.commit();
        System.out.println("Author added, with authorId=" + author.getAuthorId());
        entityManager.close();
        return author;
    }

    public Author findAuthorById(int authorId){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Author author = entityManager.find(Author.class,authorId);
        transaction.commit();
        entityManager.close();
        return author;
    }

    public  List<Author> fetchAll(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Query query = entityManager.createQuery("from Author");
        List<Author> authorList = query.getResultList();
        transaction.commit();
        entityManager.close();
        return authorList;
    }

    public Author updateAuthor(Author author){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        author = entityManager.merge(author);
        transaction.commit();
        entityManager.close();
        return author;
    }

    public boolean removeAuthorById(int authorId){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Author author = entityManager.find(Author.class,authorId);
        if(author == null)
            return false;
        entityManager.remove(author);
        transaction.commit();
        entityManager.close();
        return true;
    }

    void printAuthor(Author author) {
        System.out.println("Author Details : \n---------------------------------\n"+author.getAuthorId()+" "
                +author.getFirstName()+" "+author.getMiddleName()+" "+author.getLastName());
    }

}
