state ALU::Display.z_displaying_error ( message: in string ) is
begin
  // send the error to the display
  disp~>error( message );
end state;
