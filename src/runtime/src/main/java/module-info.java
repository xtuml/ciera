import io.ciera.runtime.api.application.Application;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.application.BaseApplication;

module io.ciera.runtime {
    exports io.ciera.runtime.domain;
    exports io.ciera.runtime.application;
    exports io.ciera.runtime.time;
    
    uses Domain;
    
    provides Application with BaseApplication;

    requires transitive io.ciera.runtime.api;
    requires transitive java.logging;
}