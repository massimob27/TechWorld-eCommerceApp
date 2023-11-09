package com.example.techworld.model;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class DatabaseManager {

    private static Connection con = null;

    public void createConnection() throws SQLException {
        String url = "jdbc:sqlite:techworld.db";
        con = DriverManager.getConnection(url);
        if (con != null && !con.isClosed())
            System.out.println("Connected!");
    }

    public void closeConnection() throws SQLException {
        if(con != null)
            con.close();
        con = null;
    }



    // METODI DB

    public User findUserByUsername(String username){

        User utente = null;

        String query = "select * from utente where username = ?";

        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                utente = new User(rs.getString("username"),rs.getString("password"),
                        rs.getString("azienda"),rs.getBoolean("venditore"),
                        rs.getString("num_telefono"), rs.getString("email"));

            }
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utente;
    }


    public Vector<String> findEmails(){

        Vector<String> emails = new Vector<String>();

        String query = "select email from utente";

        try {
            PreparedStatement st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                emails.add(rs.getString("email"));
            }
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emails;

    }

    public Vector<String> findTelephoneNumbers(){

        Vector<String> telephoneNumbers = new Vector<String>();

        String query = "select num_telefono from utente";

        try {
            PreparedStatement st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                telephoneNumbers.add(rs.getString("num_telefono"));
            }
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return telephoneNumbers;

    }


    public void insertNewUser(User user){



        String insertStr = "INSERT INTO utente VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement st;

        try {
            st = con.prepareStatement(insertStr);
            st.setString(1, user.username());
            st.setString(2, user.password());
            st.setString(3, user.azienda());
            st.setBoolean(4, user.venditore());
            st.setString(5, user.num_telefono());
            st.setString(6, user.email());


            st.executeUpdate();
            st.close();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean verificaLogin(String username, String password) {

        String query = "select password from utente where username = ?";
        boolean correctPassword = false;

        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {

                String encryptedPassword = rs.getString("password");
                correctPassword = BCrypt.checkpw(password, encryptedPassword);

            }

            st.close();

        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return correctPassword;


    }



    public Article findArticleByTitle(String title){

        Article articolo = null;

        String query = "select * from articolo where nome_articolo = ?";

        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, title);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {

                articolo = new Article(rs.getString("nome_articolo"),rs.getString("accountCreatore"),
                        rs.getDouble("costo_singolo"),rs.getString("descrizione"));


            }
            st.close();

        }

        catch (SQLException e) {
            e.printStackTrace();
        }


        return articolo;


    }


    public void insertNewArticle(Article article){

        String insertStr = "INSERT INTO articolo VALUES (?, ?, ?, ?)";

        PreparedStatement st;

        try {

            st = con.prepareStatement(insertStr);

            st.setString(1, article.nome_articolo());
            st.setString(2, article.accountCreatore());
            st.setDouble(3, article.costo_singolo());
            st.setString(4, article.descrizione());


            st.executeUpdate();
            st.close();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public Vector<Article> findArticles(){

        Vector<Article> articles = new Vector<Article>();

        String query = "select * from articolo";

        Article article = null;

        try {
            PreparedStatement st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                article = new Article(rs.getString("nome_articolo"),rs.getString("accountCreatore"),
                        rs.getDouble("costo_singolo"),rs.getString("descrizione"));

                articles.add(article);


            }

            st.close();

        }


        catch (SQLException e) {
            e.printStackTrace();
        }

        return articles;

    }


    public void addToCart(String username, String nome_articolo) {
        if( UserHasArticle(username, nome_articolo) == 0 ) {
            String insertStr = "INSERT INTO carrello VALUES (?,?,?)";
            PreparedStatement st;
            try {
                st = con.prepareStatement(insertStr);
                st.setString(1,username );
                st.setString(2,nome_articolo );
                st.setInt(3, 1);

                st.executeUpdate();
                st.close();


            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        }
        else {
            String updateStr = "UPDATE carrello set istanze_desiderate = istanze_desiderate+1  where username=? and articolo=? ";
            PreparedStatement st;
            try {
                st = con.prepareStatement(updateStr);
                st.setString(1, username);
                st.setString(2, nome_articolo);

                st.executeUpdate();
                st.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }


    public int UserHasArticle(String username, String nome_articolo) {

        String query = "select * from carrello where username = ? and articolo = ?";
        int numeroArticoli=0;

        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, username );
            st.setString(2, nome_articolo);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                numeroArticoli = rs.getInt("istanze_desiderate");
            }
            st.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return numeroArticoli;
    }




    public Vector<CartInstance> findCartByUser(String username){

        Vector<CartInstance> carrello = new Vector<CartInstance>();

        CartInstance cartInstance = null;

        String query = "select * from carrello where username = ?";

        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                cartInstance = new CartInstance(rs.getString("username"), rs.getString("articolo"),
                        rs.getInt("istanze_desiderate"));

                carrello.add(cartInstance);
            }
            st.close();


        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return carrello;

    }


    public void removeFromCart(String username, String articolo ) {
        String delStr= "DELETE FROM carrello where username = ? and articolo = ? ";
        PreparedStatement st;
        try {
            st = con.prepareStatement(delStr);
            st.setString(1, username );
            st.setString(2, articolo );

            st.executeUpdate();
            st.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeInstanceFromCart(String username, String articolo){
        String updateStr = "UPDATE carrello set istanze_desiderate = istanze_desiderate-1  where username=? and articolo=? ";
        PreparedStatement st;
        try {
            st = con.prepareStatement(updateStr);
            st.setString(1, username);
            st.setString(2, articolo);

            st.executeUpdate();
            st.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void makeOrder(Ordine ordine) {
        String insertStr = "INSERT INTO ordini VALUES (?, ?, ?, ?, ?, ?)";

        try {

            PreparedStatement st = con.prepareStatement(insertStr);

            st.setString(1, ordine.id());
            st.setString(2, ordine.username());
            st.setString(3, ordine.articolo());
            st.setInt(4, ordine.istanze());
            st.setDouble(5, ordine.costo_totale());
            st.setString(6, ordine.data());

            st.executeUpdate();
            st.close();

        }

        catch (SQLException e) {
            e.printStackTrace();
        }



    }

    public ArrayList<Ordine> findOrdersByUser(String username){
        Ordine ordine = null;

        ArrayList<Ordine> orders = new ArrayList<Ordine>();

        String query = "select * from ordini where username = ?";

        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ordine = new Ordine(rs.getString("id"), rs.getString("username"),
                        rs.getString("articolo"), rs.getInt("istanze"),
                        rs.getDouble("costo_totale"), rs.getString("data"));

                orders.add(ordine);

            }
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }






}