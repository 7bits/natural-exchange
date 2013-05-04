package it.sevenbits.entity;

/**
 *Класс, представляющий сущность таблицы User а БД
 */
public class User {
	private int id;
	private String first_name;
	
	public	User(){
		id = 0;
		first_name = null;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setFirstName(String value){
		this.first_name = value;
	}
	
	public String getFirstName(){
		return this.first_name;
	}
}
