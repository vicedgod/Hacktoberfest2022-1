import java.util.Collection;
import java.util.Iterator;

public class GenericListVersion2 implements Iterable{
    
    private static final int DEFAULT_CAP = 10;
    
    
    private Object[] iValues;
    private int iSize;
    
    private class GenericListIterator implements Iterator{
        private int position;
        private boolean removeOK;
        
        private GenericListIterator(){
            position = 0;
            removeOK = false;
        }
        
        public boolean hasNext(){
            return position < iSize;
        }
        
        public Object next(){
            Object result = iValues[position];
            position++;
            removeOK = true;
            return result;
        }
        
        public void remove(){
            if( !removeOK )
                throw new IllegalStateException();
            
            removeOK = false;
            GenericListVersion2.this.remove(position - 1);
            position--;
        }
    }
    
    public Iterator iterator(){
        return new GenericListIterator();
    }
    
    public void addAll(Collection c){
       
        for(Object obj : c){
            this.add(obj);
        }
    }
    
    
    public void add(Object x){
        insert(iSize, x);
    }
    
    public Object get(int pos){
        return iValues[pos];
    }
    
    
    public void insert(int pos, Object obj){
        ensureCapcity();
        for(int i = iSize; i > pos; i--){
            iValues[i] = iValues[i - 1];
        }
        iValues[pos] = obj;
        iSize++;
    }
    
    public Object remove(int pos){
        Object removedValue = iValues[pos];
        for(int i = pos; i < iSize - 1; i++)
            iValues[i] = iValues[i + 1];
        iValues[iSize - 1] = null;
        iSize--;
        return removedValue;
    }
    
    private void ensureCapcity(){
        
        if(iSize == iValues.length)
            resize();
    }
    
    public int size(){
        return iSize;
    }
    
    
    private void resize() {
        Object[] temp = new Object[iValues.length * 2];
        System.arraycopy(iValues, 0, temp, 0, iValues.length);
        iValues = temp;
    }
    
    
    public String toString(){
        
        String result = "size: " + iSize + ", elements: [";
        for(int i = 0; i < iSize - 1; i++)
            result += iValues[i].toString() + ", ";
        if(iSize > 0 )
            result += iValues[iSize - 1];
        result += "]";
        return result;
    }
    
    
    public String toStringUsingStringBuffer(){
        StringBuffer result = new StringBuffer();
        result.append( "size: " );
        result.append( iSize );
        result.append(", elements: [");
        for(int i = 0; i < iSize - 1; i++){
            result.append(iValues[i]);
            result.append(", ");
        }
        if( iSize > 0 )
            result.append(iValues[iSize - 1]);
        result.append("]");
        return result.toString();
    }

    
    public GenericListVersion2(){
        
        this(DEFAULT_CAP);
        
    }

    
    public GenericListVersion2(int initialCap) {
        assert initialCap > 0 : "Violation of precondition. IntListVer1(int initialCap):"
            + "initialCap must be greater than 0. Value of initialCap: " + initialCap;
        iValues = new Object[initialCap];
        iSize = 0;
    }
    
   
    public boolean equals(Object other){
        boolean result;
        if(other == null)
            
            result = false;
        else if(this == other)
            
            result = true;
        else if( this.getClass() != other.getClass() )
            
            result = false;
        else{
            
            GenericListVersion2 otherList = (GenericListVersion2)other;
            result = this.size() == otherList.size();
            int i = 0;
            while(i < iSize && result){
                result = this.iValues[i].equals( otherList.iValues[i] );
                i++;
            }
        }
        return result;     
    }

}

//this code is written and contributed by AMAN KUMAR BEHERA in the month of october.
