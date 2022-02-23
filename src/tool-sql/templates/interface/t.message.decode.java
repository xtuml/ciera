    @Override
.if (body)
    @MessageMapping( "/${self.msg_name}${suffix}" )
.end if
    public void ${self.msg_name}( ${self.msg_name}Msg message  ) throws Exception {
.if (body)
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
.else
        // empty to satisfy override of interface definition
.end if;
    }
