terraform {
  required_providers {
    regru = {
      source = "hasan2001jk/regru"
      version = "0.0.2"
    }
  }
}

resource "regru_server" "kvm_server" {
    name = "Linux_Server_PROD"
    size = "base-2"
    image = "ubuntu-22-04-amd64"
    token = "c6d9db9d9386f58e247d824debcdc8779bf06505a5c33b596282632ca86744780df7cd8c8ab46846d72675245e436bc8"
}


resource "regru_ssh" "example_ssh_key" {
  name       = "ssh_ansible"
  public_key = "ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIHd9dOaD/kDfnuLQrHwnw8AkflRR+B57vsh4VV5l6aD5 root@st17-HP-EliteDesk-800-G1-SFF"
  token      = "c6d9db9d9386f58e247d824debcdc8779bf06505a5c33b596282632ca86744780df7cd8c8ab46846d72675245e436bc8"
}
