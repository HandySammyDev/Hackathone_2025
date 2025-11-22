package database;

public interface ObjDecoder<T>
{
    public T deserialize(String data);
}
