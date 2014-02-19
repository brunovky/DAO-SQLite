package com.goldroid.db.dao;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.goldroid.db.factory.DBHelper;

public class AbstractDAO {

	private static SQLiteDatabase db;

	public AbstractDAO(Context context) {
		if (db == null) {
			AbstractDAO.db = new DBHelper(context).getDatabase();
		}
	}

	public long insert(Object model) {
		ContentValues values = new ContentValues();
		Field[] fields = model.getClass().getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				if (!field.getName().equals("id")) {
					if (field.getType().equals(String.class)) {
						values.put(field.getName(), field.get(model).toString());
					} else if (field.getType().equals(int.class)) {
						values.put(field.getName(),
								Integer.parseInt(field.get(model).toString()));
					} else if (field.getType().equals(Date.class)) {
						values.put(
								field.getName(),
								DateFormat.getDateInstance(DateFormat.MEDIUM,
										new Locale("pt", "BR")).format(
										field.get(model)));
					} else if (field.getType().equals(double.class)) {
						values.put(field.getName(),
								Double.parseDouble(field.get(model).toString()));
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return db.insert(model.getClass().getSimpleName(), null, values);
	}

	public int update(Object model, String where) {
		ContentValues values = new ContentValues();
		Field[] fields = model.getClass().getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				if (!field.getName().equals("id")) {
					if (field.getType().equals(String.class)) {
						values.put(field.getName(), field.get(model).toString());
					} else if (field.getType().equals(int.class)) {
						values.put(field.getName(),
								Integer.parseInt(field.get(model).toString()));
					} else if (field.getType().equals(Date.class)) {
						values.put(
								field.getName(),
								DateFormat.getDateInstance(DateFormat.MEDIUM,
										new Locale("pt", "BR")).format(
										field.get(model)));
					} else if (field.getType().equals(double.class)) {
						values.put(field.getName(),
								Double.parseDouble(field.get(model).toString()));
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return db.update(model.getClass().getSimpleName(), values, where, null);
	}

	public int delete(Class<?> modelClass, String where) {
		return db.delete(modelClass.getSimpleName(), where, null);
	}

	public Object selectOne(Class<?> modelClass, String where, String orderBy) {
		Cursor c = db.query(modelClass.getSimpleName(),
				getColumns(modelClass.getDeclaredFields()), where, null, null,
				null, orderBy);
		c.moveToFirst();
		if (c.getCount() > 0) {
			try {
				Object object = modelClass.newInstance();
				int cont = 0;
				for (Field field : modelClass.getDeclaredFields()) {
					field.setAccessible(true);
					if (field.getType().equals(String.class)) {
						field.set(object, c.getString(cont));
					} else if (field.getType().equals(int.class)) {
						field.set(object, c.getInt(cont));
					} else if (field.getType().equals(Date.class)) {
						field.set(
								object,
								DateFormat.getDateInstance(DateFormat.MEDIUM,
										new Locale("pt", "BR")).parse(
										c.getString(cont)));
					} else if (field.getType().equals(double.class)) {
						field.set(object, c.getDouble(cont));
					}
					cont++;
				}
				c.close();
				return object;
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		c.close();
		return null;
	}

	public List<?> selectMany(Class<?> modelClass, String where) {
		Cursor c = db.query(modelClass.getSimpleName(),
				getColumns(modelClass.getDeclaredFields()), where, null, null,
				null, null);
		c.moveToFirst();
		List<Object> entities = new ArrayList<Object>();
		if (c.getCount() > 0) {
			do {
				try {
					Object object = modelClass.newInstance();
					int cont = 0;
					for (Field field : object.getClass().getDeclaredFields()) {
						field.setAccessible(true);
						if (field.getType().equals(String.class)) {
							field.set(object, c.getString(cont));
						} else if (field.getType().equals(int.class)) {
							field.set(object, c.getInt(cont));
						} else if (field.getType().equals(Date.class)) {
							field.set(
									object,
									DateFormat.getDateInstance(
											DateFormat.MEDIUM,
											new Locale("pt", "BR")).parse(
											c.getString(cont)));
						} else if (field.getType().equals(double.class)) {
							field.set(object, c.getDouble(cont));
						}
						cont++;
					}
					entities.add(object);
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} while (c.moveToNext());
			c.close();
			return entities;
		}
		c.close();
		return null;
	}

	private String[] getColumns(Field[] fields) {
		String[] columns = new String[fields.length];
		int c = 0;
		for (Field field : fields) {
			columns[c] = field.getName();
			c++;
		}
		return columns;
	}

}