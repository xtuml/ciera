    @Override
    @MessageMapping( "/${self.msg_name}" )
    public void ${self.msg_name}( ${self.msg_name}Msg message  ) throws Exception {
        getRunContext().execute(new ReceivedMessageTask() {
            @Override
            public void run() throws XtumlException {
                try {
                    ${mname}( ${parmlist} );
                }
                catch ( Exception e ) {
                    System.out.printf( "Exception, %s, in ${mname}()\n", e );
                }
            }
        });
    }
