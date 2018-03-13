package com.techmaster.hunter.angular.imports.helper;

public interface ImportHelper {
	
	Object read( String fileName );
	Object process( Object obj );
	Object save( Object obj );
	Object execute( Object obj );

}
