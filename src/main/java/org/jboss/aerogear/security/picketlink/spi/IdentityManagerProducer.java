package org.jboss.aerogear.security.picketlink.spi;

import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.config.IdentityConfiguration;
import org.picketlink.idm.config.IdentityStoreConfiguration;
import org.picketlink.idm.internal.DefaultIdentityManager;
import org.picketlink.idm.jpa.internal.JPAIdentityStoreConfiguration;
import org.picketlink.idm.jpa.schema.CredentialObject;
import org.picketlink.idm.jpa.schema.CredentialObjectAttribute;
import org.picketlink.idm.jpa.schema.IdentityObject;
import org.picketlink.idm.jpa.schema.IdentityObjectAttribute;
import org.picketlink.idm.jpa.schema.PartitionObject;
import org.picketlink.idm.jpa.schema.RelationshipIdentityObject;
import org.picketlink.idm.jpa.schema.RelationshipObject;
import org.picketlink.idm.jpa.schema.RelationshipObjectAttribute;
import org.picketlink.idm.spi.IdentityStoreInvocationContextFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;

@ApplicationScoped
public class IdentityManagerProducer {
    private IdentityConfiguration config;
    
    public IdentityManagerProducer() {
        config = new IdentityConfiguration();

        config.addStoreConfiguration(getDefaultConfiguration());
        
    }

    @Produces @AeroGear
    public IdentityManager createIdentityManager(EntityManager entityManager) {
        
        
        IdentityManager identityManager = new DefaultIdentityManager();
        IdentityStoreInvocationContextFactory icf = new AerogearInvocationContextFactory(entityManager);

        identityManager.bootstrap(config, icf);

        return identityManager;
        
        

    }
    private IdentityStoreConfiguration getDefaultConfiguration() {
        JPAIdentityStoreConfiguration configuration = new JPAIdentityStoreConfiguration();

        configureJPAConfiguration(configuration);

        return configuration;
    }
    
    private void configureJPAConfiguration(JPAIdentityStoreConfiguration configuration) {
        configuration.setIdentityClass(IdentityObject.class);
        configuration.setAttributeClass(IdentityObjectAttribute.class);
        configuration.setRelationshipClass(RelationshipObject.class);
        configuration.setRelationshipIdentityClass(RelationshipIdentityObject.class);
        configuration.setRelationshipAttributeClass(RelationshipObjectAttribute.class);
        configuration.setCredentialClass(CredentialObject.class);
        configuration.setCredentialAttributeClass(CredentialObjectAttribute.class);
        configuration.setPartitionClass(PartitionObject.class);
    }
}
