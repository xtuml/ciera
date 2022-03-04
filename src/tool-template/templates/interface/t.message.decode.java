    @MessageMapping( "/${self.msg_name}${suffix}" )
    public void ${self.msg_name}( ${self.msg_name}Msg message  ) throws Exception {
.if (body)
        getRunContext().execute(new ReceivedMessageTask() {
            @Override
            public void run() throws XtumlException {
                try {
                    ${self.msg_name}( ${parmlist} );
                }
                catch ( Exception e ) {
                    System.out.printf( "Exception, %s, in ${self.msg_name}${suffix}()\n", e );
                }
            }
        });
.else
        // empty, to satisfy override of interface definition
.end if;
    }
