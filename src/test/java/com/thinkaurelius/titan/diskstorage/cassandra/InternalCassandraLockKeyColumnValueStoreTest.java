package com.thinkaurelius.titan.diskstorage.cassandra;

import com.thinkaurelius.titan.diskstorage.StorageException;
import org.apache.commons.configuration.Configuration;
import org.junit.BeforeClass;

import com.thinkaurelius.titan.StorageSetup;
import com.thinkaurelius.titan.diskstorage.LockKeyColumnValueStoreTest;
import com.thinkaurelius.titan.diskstorage.StorageManager;
import com.thinkaurelius.titan.graphdb.configuration.GraphDatabaseConfiguration;

public class InternalCassandraLockKeyColumnValueStoreTest extends LockKeyColumnValueStoreTest {

	@BeforeClass
	public static void startCassandra() {
    	CassandraDaemonWrapper.start(StorageSetup.cassandraYamlPath);
	}
    
    @Override
    public StorageManager openStorageManager(short idx) throws StorageException {
    	Configuration sc = StorageSetup.getCassandraStorageConfiguration();
    	sc.addProperty(StorageManager.LOCAL_LOCK_MEDIATOR_PREFIX_KEY, "cassandra-" + idx);
    	sc.addProperty(GraphDatabaseConfiguration.INSTANCE_RID_SHORT_KEY, idx);
    	sc.addProperty(GraphDatabaseConfiguration.LOCK_EXPIRE_MS, EXPIRE_MS);
    	
        return new CassandraThriftStorageManager(sc);
    }
}
