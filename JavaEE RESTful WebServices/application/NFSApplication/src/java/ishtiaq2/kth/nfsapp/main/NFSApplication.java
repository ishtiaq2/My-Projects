package ishtiaq2.kth.nfsapp.main;

import javax.ejb.Stateless;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

// Identify the base URL with annotation @ApplicationPath
@Stateless
@ApplicationPath("/nfsapp")
public class NFSApplication extends Application {
}