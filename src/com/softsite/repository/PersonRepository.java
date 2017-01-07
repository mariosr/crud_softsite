package com.softsite.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import totalcross.sql.Connection;
import totalcross.sql.DriverManager;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;
import totalcross.sys.Convert;
import totalcross.sys.Settings;
import totalcross.ui.dialog.MessageBox;

import com.softsite.auxiliaries.Gender;
import com.softsite.model.Employee;
import com.softsite.model.Manager;
import com.softsite.model.Person;

public class PersonRepository {

	private Connection dbcon;
	private Statement st;

	public PersonRepository() {
		estabelecerConexao();
	}

	public void estabelecerConexao() {
		try {
			dbcon = DriverManager.getConnection("jdbc:sqlite:"
					+ Convert.appendPath(Settings.appPath, "person.db"));
			st = dbcon.createStatement();
			st.execute("DROP TABLE IF EXISTS person");
			st.execute("create table if not exists person (name varchar, birthday varchar, cpf varchar, sex varchar, salary real"
					+ ",role varchar)");
			//st.close();
		} catch (Exception e) {
			MessageBox.showException(e, true);
		}
	}

	public void closeConnection() throws SQLException{
		st.close();
		dbcon.close();
	}
	
	public List<Person> listarPessoas(){

		List<Person> funcionarios = new ArrayList<Person>();

		try {
			ResultSet rs = st.executeQuery("select * from person");
			while (rs.next()) {
				String role = rs.getString("role");
				Person person;
				if (role.equalsIgnoreCase("Manager")) {
					person = new Manager();
				} else
					person = new Employee();

				person.setName(rs.getString("name"));
				person.setBirthday(rs.getString("birthday"));
				person.setCpf(rs.getString("cpf"));
				person.setSalary(rs.getDouble("salary"));
				Gender gender;
				if (rs.getString("sex").equalsIgnoreCase("MASCULINO"))
					gender = Gender.MASCULINO;
				else
					gender = Gender.FEMININO;
				person.setSex(gender);

				funcionarios.add(person);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return funcionarios;
	}

	public void inserirPessoa(Person person) {

		String role = "";
		if (person instanceof Manager) {
			role = "Manager";
		} else if (person instanceof Employee) {
			role = "Employee";
		}
		try {
			st.executeUpdate("insert into person values(" + "'"
					+ person.getName() + "','" 
					+ person.getBirthday() + "','"
					+ person.getCpf() + "','" + person.getSex() + "',"
					+ person.getSalary() + ",'" + role + "')");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
