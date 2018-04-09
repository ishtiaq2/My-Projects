package ishtiaq2.kth.nfsapp.file;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-18T22:49:35")
@StaticMetamodel(FFile.class)
public class FFile_ { 

    public static volatile SingularAttribute<FFile, String> owner;
    public static volatile ListAttribute<FFile, String> comments;
    public static volatile SingularAttribute<FFile, String> contents;
    public static volatile SingularAttribute<FFile, Integer> id;
    public static volatile SingularAttribute<FFile, String> title;

}