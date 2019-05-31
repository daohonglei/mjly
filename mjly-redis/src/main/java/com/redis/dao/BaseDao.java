package com.redis.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

public class BaseDao {
	@Autowired
    protected StringRedisTemplate redisTemplate;
	
	public void save(final Long key,final Object object,final Long expireTime) {
        redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
//                if(connection.set(toByteArray(key),toByteArray(object))){
//                	//connection.expire(toByteArray(key), expireTime);
//        		}
                
                connection.set(toByteArray(key),toByteArray(object));
        		
                
                return null;
            }
        });
    }
    
    public Object get(final Long id) {
        return redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] value = connection.get(toByteArray(id));
                return toObject(value);
            }
        });
    }
    
    public void hSet(final Object key,final Object field, final Object value) {
 	   redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyb = toByteArray(key);
                byte[] valueb = toByteArray(value);
                byte[] fieldb = toByteArray(field);
                connection.hSet(keyb, fieldb, valueb);
                connection.close();
                return 1L;
            }
        });
    }
    
    public void hMSet(final Object key, final Map<byte[],byte[]> bMap){
 	   redisTemplate.execute(new RedisCallback<Long>() {
           public Long doInRedis(RedisConnection connection) throws DataAccessException {
               byte[] keyb =toByteArray(key);
               connection.hMSet(keyb,bMap);
               connection.close();
               return 1L;
           }
       });
    }
    
    public Object hGet(final Object key,final Object field){
 	   Object object;
 	   object=redisTemplate.execute(new RedisCallback<Object>() {
           public Object doInRedis(RedisConnection connection) throws DataAccessException {
               byte[] keyb = toByteArray(key);
               byte[] fieldb = toByteArray(field);
               byte[] value=connection.hGet(keyb, fieldb);
               connection.close();
               return toObject(value);
           }
       });
 	   return object;
    } 
    
    //byte[][] bb=new byte[5][];   5个bytes[] 数组的数组
    
    public  List<Object> hMGet(final Object key,final byte[]... keys){
 	   List<Object> list;
 	   list=redisTemplate.execute(new RedisCallback<List<Object>>() {
           public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
               byte[] keyb =toByteArray(key);
               List<byte[]> value=connection.hMGet(keyb, keys);
               connection.close();
               List<Object> list=new ArrayList<Object>();
               for (byte[] bs : value) {
             	  list.add(toObject(bs));
 			  }
               return list;
           }
       });
 	   return list;
    } 
    
    public void sAdd(final Object key,final Object value){
 	   redisTemplate.execute(new RedisCallback<Long>() {
           public Long doInRedis(RedisConnection connection) throws DataAccessException {
               byte[] keyb = key.toString().getBytes();
               byte[] valueb = toByteArray(value);
               connection.sAdd(keyb, valueb);
               connection.close();
               return 1l;
           }
       });
    } 
    
    
    public List<Object> sInter(final byte[]... keys){
 	   List<Object> list;
 	   list=redisTemplate.execute(new RedisCallback<List<Object>>() {
           public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
               List<Object> list=new ArrayList<Object>();
               Set<byte[]> set=connection.sInter(keys);
               connection.close();
               for (byte[] bs : set) {
             	  list.add(toObject(bs));
 			   }
               return list;
           }
        });
 	   return list;
    }
    
    public Set<byte[]> sInterByte(final byte[]... keys){
 	   Set<byte[]> set;
 	   set=redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
           public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
               Set<byte[]> set=connection.sInter(keys);
               connection.close();
               return set;
           }
        });
 	   return set;
    } 

	
	private Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }
 
    private byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }
}
