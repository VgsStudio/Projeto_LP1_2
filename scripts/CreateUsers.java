import group.mpntm.server.database.repo.RepositoryMySQL;

public class CreateUsers {
    public static void main(String[] args) {
        RepositoryMySQL.createLogin("admin", "admin");
        RepositoryMySQL.createLogin("iguinbariloche", "teste123");
        RepositoryMySQL.createLogin("mrcalvin", "teste123");
    }
}
